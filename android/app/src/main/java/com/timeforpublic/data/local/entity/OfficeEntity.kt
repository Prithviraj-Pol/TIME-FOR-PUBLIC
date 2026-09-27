package com.timeforpublic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timeforpublic.domain.model.GovernmentOffice

@Entity(tableName = "offices")
data class OfficeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val department: String,
    val address: String,
    val district: String,
    val state: String,
    val pinCode: String,
    val latitude: Double,
    val longitude: Double,
    val geofenceRadiusMeters: Float = 200f,
    val workingHours: String,
    val contactPhone: String
) {
    fun toDomain(): GovernmentOffice = GovernmentOffice(
        id = id,
        name = name,
        department = department,
        address = address,
        district = district,
        state = state,
        pinCode = pinCode,
        latitude = latitude,
        longitude = longitude,
        geofenceRadiusMeters = geofenceRadiusMeters,
        workingHours = workingHours,
        contactPhone = contactPhone
    )

    companion object {
        fun fromDomain(office: GovernmentOffice): OfficeEntity = OfficeEntity(
            id = office.id,
            name = office.name,
            department = office.department,
            address = office.address,
            district = office.district,
            state = office.state,
            pinCode = office.pinCode,
            latitude = office.latitude,
            longitude = office.longitude,
            geofenceRadiusMeters = office.geofenceRadiusMeters,
            workingHours = office.workingHours,
            contactPhone = office.contactPhone
        )
    }
}
