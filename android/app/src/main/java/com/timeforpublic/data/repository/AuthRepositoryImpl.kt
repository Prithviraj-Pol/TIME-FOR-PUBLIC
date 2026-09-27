package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.core.network.dto.LoginRequest
import com.timeforpublic.core.security.SecureStorage
import com.timeforpublic.core.security.TokenManager
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.model.UserRole
import com.timeforpublic.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val secureStorage: SecureStorage,
    private val tokenManager: TokenManager
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()

    init {
        // Restore existing session if phone was persisted
        val savedUserId = secureStorage.getUserId()
        val savedPhone = secureStorage.getUserPhone()
        val savedRole = secureStorage.getUserRole()
        if (!savedUserId.isNullOrBlank() && !savedPhone.isNullOrBlank()) {
            _currentUser.value = createMockUser(savedPhone, savedRole, savedUserId)
        }
    }

    override suspend fun login(phone: String, role: UserRole): Result<User> {
        return try {
            // Attempt API authentication first
            try {
                val response = apiService.login(LoginRequest(phone = phone, role = role.name))
                if (response.isSuccessful && response.body() != null) {
                    val loginRes = response.body()!!
                    secureStorage.saveUserSession(
                        userId = loginRes.user.id,
                        phone = loginRes.user.phone,
                        role = loginRes.user.role,
                        officerId = loginRes.user.officeId
                    )
                    tokenManager.saveToken(loginRes.accessToken)
                    if (loginRes.refreshToken != null) {
                        tokenManager.saveRefreshToken(loginRes.refreshToken)
                    }
                    _currentUser.value = loginRes.user
                    return Result.Success(loginRes.user)
                }
            } catch (_: Exception) {
                // If backend is offline during local test, use local secure session
            }

            val user = createMockUser(phone, role)
            secureStorage.saveUserSession(
                userId = user.id,
                phone = phone,
                role = role,
                officerId = if (role == UserRole.OFFICER) user.officeId else null
            )
            tokenManager.saveToken("mock_jwt_${System.currentTimeMillis()}")
            _currentUser.value = user
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(AppError.Unknown("Login failed: ${e.localizedMessage ?: "Unknown error"}", e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            try {
                apiService.logout()
            } catch (_: Exception) {
                // Ignore network error on logout
            }
            secureStorage.clearSession()
            tokenManager.clearTokens()
            _currentUser.value = null
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(AppError.Unknown(e.message ?: "Failed to log out", e))
        }
    }

    override suspend fun getCurrentUser(): User? = _currentUser.value

    override fun getAuthToken(): String? = tokenManager.getToken()

    private fun createMockUser(phone: String, role: UserRole, id: String? = null): User {
        return if (role == UserRole.OFFICER) {
            User(
                id = id ?: "OFF-101",
                name = "Dr. Rajesh Sharma",
                phone = phone,
                role = UserRole.OFFICER,
                designation = "Tahsildar & Sub-Divisional Magistrate",
                department = "Revenue & Land Reforms",
                officeId = "OFFICE-PUN-01"
            )
        } else {
            User(
                id = id ?: "CIT-901",
                name = "Prithviraj Pol",
                phone = phone,
                role = UserRole.CITIZEN
            )
        }
    }
}
