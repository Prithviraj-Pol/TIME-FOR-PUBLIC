package com.timeforpublic.domain.usecase.documents

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.repository.DocumentRepository
import javax.inject.Inject

class VerifyDocumentEligibilityUseCase @Inject constructor(
    private val documentRepository: DocumentRepository
) {
    suspend operator fun invoke(serviceId: String, userInputs: Map<String, Any>): Result<Boolean> {
        return documentRepository.checkEligibility(serviceId, userInputs)
    }
}
