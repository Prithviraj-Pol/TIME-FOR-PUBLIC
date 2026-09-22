package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.ServiceChecklist
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {
    fun getServiceChecklists(): Flow<Result<List<ServiceChecklist>>>
    suspend fun getChecklistByServiceId(serviceId: String): Result<ServiceChecklist>
    suspend fun checkEligibility(serviceId: String, userInputs: Map<String, Any>): Result<Boolean>
}
