package com.timeforpublic.domain.usecase.ai

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AiGuidanceResponse
import com.timeforpublic.domain.repository.AiAssistantRepository
import javax.inject.Inject

class QueryAiGuidanceUseCase @Inject constructor(
    private val aiRepository: AiAssistantRepository
) {
    suspend operator fun invoke(query: String, context: String = ""): Result<AiGuidanceResponse> {
        return aiRepository.askQuestion(query, context)
    }
}
