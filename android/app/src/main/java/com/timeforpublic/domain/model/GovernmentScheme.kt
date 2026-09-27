package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Government scheme or welfare program information.
 * Provides guidance on eligibility, benefits, and required documents.
 * Links to official government portals for actual application processing.
 */
@Serializable
data class GovernmentScheme(
    val id: String,
    val title: String,
    val department: String,
    val category: String,
    val description: String,
    val eligibilityCriteria: List<String> = emptyList(),
    val benefits: String = "",
    val requiredDocuments: List<String> = emptyList(),
    val applicationUrl: String = "",
    val isCentralGovt: Boolean = true
)
