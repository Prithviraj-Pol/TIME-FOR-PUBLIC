package com.timeforpublic.domain.usecase.documents

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.ServiceChecklist
import com.timeforpublic.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChecklistUseCase @Inject constructor(
    private val documentRepository: DocumentRepository
) {
    operator fun invoke(): Flow<Result<List<ServiceChecklist>>> {
        return documentRepository.getServiceChecklists()
    }

    suspend fun getByServiceId(serviceId: String): Result<ServiceChecklist> {
        return documentRepository.getChecklistByServiceId(serviceId)
    }
}
