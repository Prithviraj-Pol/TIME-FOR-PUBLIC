package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Government scholarship guidance model.
 */
@Serializable
data class Scholarship(
    val id: String,
    val title: String,
    val targetEducationLevel: String,
    val awardAmount: String,
    val deadline: String,
    val eligibilityCriteria: List<String> = emptyList(),
    val portalUrl: String = ""
)
