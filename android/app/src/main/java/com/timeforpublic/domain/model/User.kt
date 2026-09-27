package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * User roles supported by the platform.
 * Backend enforces role-based authorization; client role is advisory only.
 */
@Serializable
enum class UserRole {
    CITIZEN,
    OFFICER
}

/**
 * Authenticated user representation.
 * Contains only data the user is authorized to see about themselves.
 */
@Serializable
data class User(
    val id: String,
    val name: String,
    val phone: String,
    val role: UserRole,
    val designation: String? = null,
    val department: String? = null,
    val officeId: String? = null
)
