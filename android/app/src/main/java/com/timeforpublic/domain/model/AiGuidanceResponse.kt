package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * AI assistant response grounded in official government documents.
 * The AI must never invent government requirements.
 */
@Serializable
data class AiGuidanceResponse(
    val id: String,
    val query: String,
    val answer: String,
    val groundedSources: List<OfficialSourceMetadata> = emptyList(),
    val timestamp: Long = 0L
)

/**
 * Metadata for official government source documents
 * that ground an AI assistant response.
 */
@Serializable
data class OfficialSourceMetadata(
    val title: String,
    val department: String,
    val referenceUrl: String = ""
)
