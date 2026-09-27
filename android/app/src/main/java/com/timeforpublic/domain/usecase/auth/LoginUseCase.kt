package com.timeforpublic.domain.usecase.auth

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.model.UserRole
import com.timeforpublic.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phone: String, role: UserRole): Result<User> {
        return authRepository.login(phone, role)
    }
}
