package com.timeforpublic.domain.usecase.auth

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return authRepository.logout()
    }
}
