package com.timeforpublic.domain.model

data class GovernmentOffice(
    val id: String,
    val name: String,
    val department: String,
    val address: String,
    val district: String,
    val state: String,
    val pinCode: String,
    val latitude: Double,
    val longitude: Double,
    val workingHours: String,
    val contactPhone: String,
    val totalOfficersOnDuty: Int = 0
)
