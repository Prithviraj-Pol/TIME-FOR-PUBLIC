package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import kotlinx.coroutines.flow.Flow

interface OfficerRepository {
    fun getOfficersByOffice(officeId: String): Flow<Result<List<OfficerStatus>>>
    fun getAllLiveOfficers(): Flow<Result<List<OfficerStatus>>>
    suspend fun getOfficerById(officerId: String): Result<OfficerStatus>
    suspend fun updateOfficerStatus(
        officerId: String,
        status: AvailabilityStatus,
        note: String,
        isGeofenceVerified: Boolean
    ): Result<OfficerStatus>
}
