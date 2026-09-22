package com.timeforpublic.domain.model

enum class AvailabilityStatus {
    AVAILABLE,
    IN_MEETING,
    ON_FIELD_DUTY,
    ON_LEAVE,
    OFFLINE
}

data class OfficerStatus(
    val officerId: String,
    val name: String,
    val designation: String,
    val department: String,
    val officeName: String,
    val roomNumber: String,
    val status: AvailabilityStatus,
    val statusNote: String = "",
    val activeQueueCount: Int = 0,
    val lastUpdated: String = "",
    val isGeofenceVerified: Boolean = false
)
