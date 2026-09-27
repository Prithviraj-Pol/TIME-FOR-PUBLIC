package com.timeforpublic.domain.model

import kotlinx.serialization.Serializable

/**
 * Government office or administrative building.
 * Geofence radius defaults to 200 meters per architectural specification.
 * Latitude/longitude used only for geofence registration — never exposed to citizens.
 */
@Serializable
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
    val geofenceRadiusMeters: Float = 200f,
    val workingHours: String = "",
    val contactPhone: String = ""
)
