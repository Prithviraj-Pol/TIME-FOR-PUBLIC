package com.timeforpublic.data.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.repository.OfficeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfficeRepositoryImpl(
    private val apiService: ApiService
) : OfficeRepository {

    private val seedOffices = listOf(
        GovernmentOffice(
            id = "OFFICE-PUN-01",
            name = "Sub-Divisional Magistrate & Tahsil Office",
            department = "Revenue & Land Reforms",
            address = "Shivaji Nagar Administrative Complex, Sector 4",
            district = "Pune",
            state = "Maharashtra",
            pinCode = "411005",
            latitude = 18.5314,
            longitude = 73.8446,
            workingHours = "10:00 AM - 05:30 PM (Mon - Fri)",
            contactPhone = "020-25531234",
            totalOfficersOnDuty = 4
        ),
        GovernmentOffice(
            id = "OFFICE-PUN-02",
            name = "Regional Transport Office (RTO) Central",
            department = "Transport Department",
            address = "Near Sangam Bridge, RTO Chowk",
            district = "Pune",
            state = "Maharashtra",
            pinCode = "411001",
            latitude = 18.5292,
            longitude = 73.8643,
            workingHours = "09:30 AM - 05:00 PM (Mon - Fri)",
            contactPhone = "020-26051152",
            totalOfficersOnDuty = 6
        ),
        GovernmentOffice(
            id = "OFFICE-PUN-03",
            name = "Municipal Corporation Citizen Facilitation Center (CFC)",
            department = "Urban Local Body / Municipal",
            address = "Shivaji Road, Near Kasba Peth",
            district = "Pune",
            state = "Maharashtra",
            pinCode = "411011",
            latitude = 18.5196,
            longitude = 73.8553,
            workingHours = "09:00 AM - 06:00 PM (Mon - Sat)",
            contactPhone = "020-25501000",
            totalOfficersOnDuty = 5
        )
    )

    override fun getOffices(): Flow<Result<List<GovernmentOffice>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(seedOffices))
        } catch (e: Exception) {
            emit(Result.Error("Unable to load offices", e))
        }
    }

    override suspend fun getOfficeById(id: String): Result<GovernmentOffice> {
        val office = seedOffices.find { it.id == id }
        return if (office != null) Result.Success(office)
        else Result.Error("Office with ID $id not found")
    }

    override suspend fun getOfficesByDepartment(department: String): Result<List<GovernmentOffice>> {
        val filtered = seedOffices.filter { it.department.contains(department, ignoreCase = true) }
        return Result.Success(filtered)
    }
}
