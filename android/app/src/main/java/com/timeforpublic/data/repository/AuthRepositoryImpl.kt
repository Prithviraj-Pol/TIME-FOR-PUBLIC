package com.timeforpublic.data.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.core.security.SecureStorage
import com.timeforpublic.core.security.TokenManager
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.model.UserRole
import com.timeforpublic.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val secureStorage: SecureStorage,
    private val tokenManager: TokenManager
) : AuthRepository {

    private val _currentUser = MutableStateFlow<User?>(null)
    override val currentUser: Flow<User?> = _currentUser.asStateFlow()

    init {
        // Restore existing session if phone was persisted
        val savedPhone = secureStorage.getUserPhone()
        val savedRole = secureStorage.getUserRole()
        if (!savedPhone.isNullOrBlank()) {
            _currentUser.value = createMockUser(savedPhone, savedRole)
        }
    }

    override suspend fun login(phone: String, role: UserRole): Result<User> {
        return try {
            val user = createMockUser(phone, role)
            secureStorage.saveUserSession(phone, role, if (role == UserRole.OFFICER) user.id else null)
            tokenManager.saveToken("mock_jwt_${System.currentTimeMillis()}")
            _currentUser.value = user
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error("Login failed: ${e.localizedMessage ?: "Unknown error"}", e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        secureStorage.clearSession()
        tokenManager.clearToken()
        _currentUser.value = null
        return Result.Success(Unit)
    }

    override suspend fun getCurrentUser(): User? = _currentUser.value

    private fun createMockUser(phone: String, role: UserRole): User {
        return if (role == UserRole.OFFICER) {
            User(
                id = "OFF-101",
                name = "Dr. Rajesh Sharma",
                phone = phone,
                role = UserRole.OFFICER,
                designation = "Tahsildar & Sub-Divisional Magistrate",
                department = "Revenue & Land Reforms",
                officeId = "OFFICE-PUN-01"
            )
        } else {
            User(
                id = "CIT-901",
                name = "Prithviraj Pol",
                phone = phone,
                role = UserRole.CITIZEN
            )
        }
    }
}
