package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.data.local.dao.OfficeDao
import com.timeforpublic.data.local.entity.OfficeEntity
import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.repository.OfficeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfficeRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val officeDao: OfficeDao
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
            geofenceRadiusMeters = 200f,
            workingHours = "10:00 AM - 05:30 PM (Mon - Fri)",
            contactPhone = "020-25531234"
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
            geofenceRadiusMeters = 200f,
            workingHours = "09:30 AM - 05:00 PM (Mon - Fri)",
            contactPhone = "020-26051152"
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
            geofenceRadiusMeters = 200f,
            workingHours = "09:00 AM - 06:00 PM (Mon - Sat)",
            contactPhone = "020-25501000"
        )
    )

    override fun getOffices(): Flow<Result<List<GovernmentOffice>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(seedOffices))
            try {
                val response = apiService.getOffices()
                if (response.isSuccessful && response.body() != null) {
                    val remoteOffices = response.body()!!
                    officeDao.upsertOffices(remoteOffices.map { OfficeEntity.fromDomain(it) })
                    emit(Result.Success(remoteOffices))
                }
            } catch (_: Exception) {
                // Keep displaying cached offices
            }
        } catch (e: Exception) {
            emit(Result.Error(AppError.Unknown(e.message ?: "Unable to load offices", e)))
        }
    }

    override suspend fun getOfficeById(id: String): Result<GovernmentOffice> {
        val office = seedOffices.find { it.id == id }
        return if (office != null) Result.Success(office)
        else Result.Error(AppError.NotFound("Office with ID $id not found"))
    }

    override suspend fun getOfficesByDepartment(department: String): Result<List<GovernmentOffice>> {
        val filtered = seedOffices.filter { it.department.contains(department, ignoreCase = true) }
        return Result.Success(filtered)
    }
}
