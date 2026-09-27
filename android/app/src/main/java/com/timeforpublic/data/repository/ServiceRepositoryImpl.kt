package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.data.local.dao.ServiceDao
import com.timeforpublic.data.local.entity.ServiceEntity
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.ServiceCategory
import com.timeforpublic.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val serviceDao: ServiceDao
) : ServiceRepository {

    private val seedServices = listOf(
        GovernmentService(
            id = "SRV-001",
            title = "Income Certificate Issuance",
            category = ServiceCategory.CERTIFICATE,
            department = "Revenue Department",
            description = "Official state document certifying annual family income from all sources.",
            eligibilitySummary = "Resident citizen with valid address and income proofs",
            requiredDocumentsSummary = listOf("Aadhaar Card", "Salary Slip/ITR/Form 16", "Ration Card", "Affidavit"),
            officialPortalUrl = "https://edistrict.gov.in",
            estimatedProcessingDays = 7
        ),
        GovernmentService(
            id = "SRV-002",
            title = "Post-Matric Scholarship for Minorities",
            category = ServiceCategory.SCHOLARSHIP,
            department = "Ministry of Minority Affairs",
            description = "Financial assistance for higher education of students belonging to notified minority communities.",
            eligibilitySummary = "Students in Class 11 to Ph.D. with >=50% marks and family income <= ₹2 Lakhs",
            requiredDocumentsSummary = listOf("Previous Marksheet", "Income Certificate", "Fee Receipt", "Bank Passbook"),
            officialPortalUrl = "https://scholarships.gov.in",
            estimatedProcessingDays = 21
        ),
        GovernmentService(
            id = "SRV-003",
            title = "PM Street Vendor's AtmaNirbhar Nidhi (PM SVANidhi)",
            category = ServiceCategory.WELFARE,
            department = "Ministry of Housing and Urban Affairs",
            description = "Micro-credit collateral free working capital loan up to ₹50,000 for urban street vendors.",
            eligibilitySummary = "Street vendors vending before March 24, 2020 with vending certificate or recommendation letter",
            requiredDocumentsSummary = listOf("Aadhaar Card", "Vending ID Card/Letter of Recommendation", "Bank Account"),
            officialPortalUrl = "https://pmsvanidhi.mohua.gov.in",
            estimatedProcessingDays = 10
        ),
        GovernmentService(
            id = "SRV-004",
            title = "Learner's & Permanent Driving License",
            category = ServiceCategory.GENERAL,
            department = "Transport Department",
            description = "Online test, biometric appointment, and contactless driving permit issuance.",
            eligibilitySummary = "Age 18+ (or 16+ for gearless 50cc). Valid address proof.",
            requiredDocumentsSummary = listOf("Age Proof", "Address Proof", "Form 1 Medical Self-Declaration"),
            officialPortalUrl = "https://parivahan.gov.in",
            estimatedProcessingDays = 5
        )
    )

    override fun getServices(category: ServiceCategory?, query: String?): Flow<Result<List<GovernmentService>>> = flow {
        emit(Result.Loading)
        try {
            // First emit cached services or seed
            var list = seedServices
            if (category != null) {
                list = list.filter { it.category == category }
            }
            if (!query.isNullOrBlank()) {
                list = list.filter {
                    it.title.contains(query, ignoreCase = true) ||
                    it.department.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true)
                }
            }
            emit(Result.Success(list))

            // Attempt remote sync if available
            try {
                val response = apiService.getServices(category?.name, query)
                if (response.isSuccessful && response.body() != null) {
                    val remoteList = response.body()!!
                    serviceDao.upsertServices(remoteList.map { ServiceEntity.fromDomain(it) })
                    emit(Result.Success(remoteList))
                }
            } catch (_: Exception) {
                // Keep cached data
            }
        } catch (e: Exception) {
            emit(Result.Error(AppError.Unknown(e.message ?: "Failed to get services", e)))
        }
    }

    override fun getServiceById(id: String): Flow<Result<GovernmentService>> = flow {
        emit(Result.Loading)
        val cached = seedServices.find { it.id == id }
        if (cached != null) {
            emit(Result.Success(cached))
        } else {
            try {
                val response = apiService.getServiceById(id)
                if (response.isSuccessful && response.body() != null) {
                    emit(Result.Success(response.body()!!))
                } else {
                    emit(Result.Error(AppError.NotFound("Service not found")))
                }
            } catch (e: Exception) {
                emit(Result.Error(AppError.NetworkError(cause = e)))
            }
        }
    }
}
