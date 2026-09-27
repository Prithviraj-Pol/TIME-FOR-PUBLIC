package com.timeforpublic.core.network.dto

import com.timeforpublic.domain.model.User
import kotlinx.serialization.Serializable

/**
 * Network DTOs for API request/response bodies.
 * Separated from domain models to decouple API contract from business logic.
 */

@Serializable
data class LoginRequest(
    val phone: String,
    val role: String
)

@Serializable
data class LoginResponse(
    val user: User,
    val accessToken: String,
    val refreshToken: String? = null
)

@Serializable
data class StatusUpdateRequest(
    val status: String,
    val note: String = "",
    val isGeofenceVerified: Boolean = false
)

@Serializable
data class GeofenceEventRequest(
    val officeId: String,
    val transitionType: String, // "ENTER" or "EXIT"
    val timestamp: Long
)

@Serializable
data class AiQueryRequest(
    val query: String,
    val context: String = ""
)
