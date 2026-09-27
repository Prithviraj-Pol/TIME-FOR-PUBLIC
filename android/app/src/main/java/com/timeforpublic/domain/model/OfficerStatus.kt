package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Officer availability states.
 * Strictly limited to 7 mandated states per product specification.
 * No appointment/queue states are permitted.
 */
@Serializable
enum class AvailabilityStatus {
    IN_OFFICE,
    OUT_OF_OFFICE,
    IN_MEETING,
    FIELD_VISIT,
    TRAINING,
    ON_LEAVE,
    UNKNOWN
}

/**
 * Officer availability information visible to citizens.
 * Contains no location coordinates — only high-level availability state
 * and the office identifier for transparency purposes.
 */
@Serializable
data class OfficerStatus(
    val officerId: String,
    val name: String,
    val designation: String,
    val department: String,
    val officeId: String,
    val officeName: String,
    val roomNumber: String = "",
    val status: AvailabilityStatus = AvailabilityStatus.UNKNOWN,
    val statusNote: String = "",
    val lastUpdated: Long = 0L,
    val isGeofenceVerified: Boolean = false
)
