package com.timeforpublic.domain.model

enum class UserRole {
    CITIZEN,
    OFFICER
}

data class User(
    val id: String,
    val name: String,
    val phone: String,
    val role: UserRole,
    val designation: String? = null,
    val department: String? = null,
    val officeId: String? = null
)
