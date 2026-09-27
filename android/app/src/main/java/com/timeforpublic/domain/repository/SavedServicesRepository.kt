package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentService
import kotlinx.coroutines.flow.Flow

interface SavedServicesRepository {
    fun getSavedServices(): Flow<List<GovernmentService>>
    suspend fun toggleSaved(service: GovernmentService): Result<Boolean>
    fun isSaved(serviceId: String): Flow<Boolean>
}
