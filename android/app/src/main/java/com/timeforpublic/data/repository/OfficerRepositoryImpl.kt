package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.core.network.dto.StatusUpdateRequest
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.repository.OfficerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficerRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OfficerRepository {

    private val liveOfficersList = MutableStateFlow(
        listOf(
            OfficerStatus(
                officerId = "OFF-101",
                name = "Dr. Rajesh Sharma",
                designation = "Tahsildar & Executive Magistrate",
                department = "Revenue Department",
                officeId = "OFFICE-01",
                officeName = "Sub-Divisional Magistrate & Tahsil Office",
                roomNumber = "Room 12, First Floor",
                status = AvailabilityStatus.IN_OFFICE,
                statusNote = "Reviewing citizen caste/income verification files",
                lastUpdated = System.currentTimeMillis() - 600_000,
                isGeofenceVerified = true
            ),
            OfficerStatus(
                officerId = "OFF-102",
                name = "Smt. Sunita Deshmukh",
                designation = "Deputy RTO Licensing Officer",
                department = "Transport Department",
                officeId = "OFFICE-02",
                officeName = "Regional Transport Office (RTO) Central",
                roomNumber = "Window 4 - Commercial Licenses",
                status = AvailabilityStatus.IN_MEETING,
                statusNote = "Quarterly departmental road safety review",
                lastUpdated = System.currentTimeMillis() - 1500_000,
                isGeofenceVerified = true
            ),
            OfficerStatus(
                officerId = "OFF-103",
                name = "Shri Anand Kulkarni",
                designation = "Town Planning Officer",
                department = "Municipal Corporation",
                officeId = "OFFICE-03",
                officeName = "Municipal Corporation CFC",
                roomNumber = "Room 108, Ground Floor",
                status = AvailabilityStatus.FIELD_VISIT,
                statusNote = "Site inspection for building plan sanction (Expected back at 3:30 PM)",
                lastUpdated = System.currentTimeMillis() - 3600_000,
                isGeofenceVerified = false
            ),
            OfficerStatus(
                officerId = "OFF-104",
                name = "Shri Vikram Singh",
                designation = "Nayab Tahsildar (Land Records)",
                department = "Revenue Department",
                officeId = "OFFICE-01",
                officeName = "Sub-Divisional Magistrate & Tahsil Office",
                roomNumber = "Room 14, First Floor",
                status = AvailabilityStatus.IN_OFFICE,
                statusNote = "Issuing verified 7/12 land extract copies",
                lastUpdated = System.currentTimeMillis() - 60_000,
                isGeofenceVerified = true
            )
        )
    )

    override fun getLiveOfficers(): Flow<Result<List<OfficerStatus>>> {
        return liveOfficersList.asStateFlow().map { Result.Success(it) }
    }

    override fun getAllLiveOfficers(): Flow<Result<List<OfficerStatus>>> = getLiveOfficers()

    override fun getOfficersByOffice(officeId: String): Flow<Result<List<OfficerStatus>>> {
        return liveOfficersList.asStateFlow().map { list ->
            Result.Success(list.filter { it.officeId == officeId })
        }
    }

    override suspend fun getOfficerById(officerId: String): Result<OfficerStatus> {
        val officer = liveOfficersList.value.find { it.officerId == officerId }
        return if (officer != null) Result.Success(officer)
        else Result.Error(AppError.NotFound("Officer with ID $officerId not found"))
    }

    override suspend fun updateAvailability(
        officerId: String,
        status: AvailabilityStatus,
        note: String,
        isGeofenceVerified: Boolean
    ): Result<OfficerStatus> {
        val currentList = liveOfficersList.value.toMutableList()
        val index = currentList.indexOfFirst { it.officerId == officerId }
        if (index != -1) {
            val updated = currentList[index].copy(
                status = status,
                statusNote = note,
                isGeofenceVerified = isGeofenceVerified,
                lastUpdated = System.currentTimeMillis()
            )
            currentList[index] = updated
            liveOfficersList.value = currentList

            // Also report to remote API if connected
            try {
                apiService.updateOfficerStatus(
                    id = officerId,
                    request = StatusUpdateRequest(
                        status = status.name,
                        note = note,
                        isGeofenceVerified = isGeofenceVerified
                    )
                )
            } catch (_: Exception) {
                // Keep local updated
            }

            return Result.Success(updated)
        }
        return Result.Error(AppError.NotFound("Officer not found"))
    }
}
