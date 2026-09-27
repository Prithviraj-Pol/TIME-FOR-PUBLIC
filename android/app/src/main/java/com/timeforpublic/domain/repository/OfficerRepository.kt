package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import kotlinx.coroutines.flow.Flow

interface OfficerRepository {
    fun getLiveOfficers(): Flow<Result<List<OfficerStatus>>>
    fun getAllLiveOfficers(): Flow<Result<List<OfficerStatus>>> = getLiveOfficers()
    fun getOfficersByOffice(officeId: String): Flow<Result<List<OfficerStatus>>>
    suspend fun getOfficerById(officerId: String): Result<OfficerStatus>
    suspend fun updateAvailability(
        officerId: String,
        status: AvailabilityStatus,
        note: String,
        isGeofenceVerified: Boolean
    ): Result<OfficerStatus>
    suspend fun updateOfficerStatus(
        officerId: String,
        status: AvailabilityStatus,
        note: String,
        isGeofenceVerified: Boolean
    ): Result<OfficerStatus> = updateAvailability(officerId, status, note, isGeofenceVerified)
}
