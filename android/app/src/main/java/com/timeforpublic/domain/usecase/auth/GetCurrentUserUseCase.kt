package com.timeforpublic.domain.usecase.auth

import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<User?> {
        return authRepository.currentUser
    }

    suspend fun getSync(): User? {
        return authRepository.getCurrentUser()
    }
}
