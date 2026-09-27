package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Citizen certificate guidance model (Caste, Income, Domicile, Birth, etc.).
 */
@Serializable
data class Certificate(
    val id: String,
    val title: String,
    val issuingAuthority: String,
    val feeInr: Int = 0,
    val validityPeriod: String = "Permanent",
    val processingDays: Int = 15,
    val requiredDocuments: List<String> = emptyList(),
    val portalUrl: String = ""
)
