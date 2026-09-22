package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.User
import com.timeforpublic.domain.model.UserRole
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    suspend fun login(phone: String, role: UserRole): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
}
