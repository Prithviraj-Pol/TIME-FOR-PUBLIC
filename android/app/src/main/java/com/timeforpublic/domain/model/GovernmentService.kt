package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Service categories covering all government service types
 * available through the TIME FOR PUBLIC platform.
 */
@Serializable
enum class ServiceCategory {
    SCHEME,
    SCHOLARSHIP,
    CERTIFICATE,
    WELFARE,
    GENERAL
}

/**
 * A government service that citizens can discover and learn about.
 * The platform provides guidance only — not application processing.
 */
@Serializable
data class GovernmentService(
    val id: String,
    val title: String,
    val category: ServiceCategory,
    val department: String,
    val description: String,
    val eligibilitySummary: String = "",
    val requiredDocumentsSummary: List<String> = emptyList(),
    val officialPortalUrl: String = "",
    val estimatedProcessingDays: Int = 0,
    val isSaved: Boolean = false
)
