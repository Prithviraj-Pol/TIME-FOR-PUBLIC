package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AiGuidanceResponse

interface AiAssistantRepository {
    suspend fun askQuestion(query: String, context: String = ""): Result<AiGuidanceResponse>
}
