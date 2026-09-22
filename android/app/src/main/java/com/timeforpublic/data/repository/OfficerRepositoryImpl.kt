package com.timeforpublic.data.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.repository.OfficerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class OfficerRepositoryImpl(
    private val apiService: ApiService
) : OfficerRepository {

    private val liveOfficersList = MutableStateFlow(
        listOf(
            OfficerStatus(
                officerId = "OFF-101",
                name = "Dr. Rajesh Sharma",
                designation = "Tahsildar & Executive Magistrate",
                department = "Revenue Department",
                officeName = "Sub-Divisional Magistrate & Tahsil Office",
                roomNumber = "Room 12, First Floor",
                status = AvailabilityStatus.AVAILABLE,
                statusNote = "Reviewing citizen caste/income verification files",
                activeQueueCount = 4,
                lastUpdated = "10 mins ago",
                isGeofenceVerified = true
            ),
            OfficerStatus(
                officerId = "OFF-102",
                name = "Smt. Sunita Deshmukh",
                designation = "Deputy RTO Licensing Officer",
                department = "Transport Department",
                officeName = "Regional Transport Office (RTO) Central",
                roomNumber = "Window 4 - Commercial Licenses",
                status = AvailabilityStatus.IN_MEETING,
                statusNote = "Quarterly departmental road safety review",
                activeQueueCount = 12,
                lastUpdated = "25 mins ago",
                isGeofenceVerified = true
            ),
            OfficerStatus(
                officerId = "OFF-103",
                name = "Shri Anand Kulkarni",
                designation = "Town Planning Officer",
                department = "Municipal Corporation",
                officeName = "Municipal Corporation CFC",
                roomNumber = "Room 108, Ground Floor",
                status = AvailabilityStatus.ON_FIELD_DUTY,
                statusNote = "Site inspection for building plan sanction (Expected back at 3:30 PM)",
                activeQueueCount = 0,
                lastUpdated = "1 hour ago",
                isGeofenceVerified = false
            ),
            OfficerStatus(
                officerId = "OFF-104",
                name = "Shri Vikram Singh",
                designation = "Nayab Tahsildar (Land Records)",
                department = "Revenue Department",
                officeName = "Sub-Divisional Magistrate & Tahsil Office",
                roomNumber = "Room 14, First Floor",
                status = AvailabilityStatus.AVAILABLE,
                statusNote = "Issuing verified 7/12 land extract copies",
                activeQueueCount = 2,
                lastUpdated = "Just now",
                isGeofenceVerified = true
            )
        )
    )

    override fun getAllLiveOfficers(): Flow<Result<List<OfficerStatus>>> {
        return liveOfficersList.asStateFlow().map { Result.Success(it) }
    }

    override fun getOfficersByOffice(officeId: String): Flow<Result<List<OfficerStatus>>> {
        return liveOfficersList.asStateFlow().map { list ->
            Result.Success(list)
        }
    }

    override suspend fun getOfficerById(officerId: String): Result<OfficerStatus> {
        val officer = liveOfficersList.value.find { it.officerId == officerId }
        return if (officer != null) Result.Success(officer)
        else Result.Error("Officer with ID $officerId not found")
    }

    override suspend fun updateOfficerStatus(
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
                lastUpdated = "Just now"
            )
            currentList[index] = updated
            liveOfficersList.value = currentList
            return Result.Success(updated)
        }
        return Result.Error("Officer not found")
    }
}
