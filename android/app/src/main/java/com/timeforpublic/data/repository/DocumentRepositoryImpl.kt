package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.domain.model.DocumentItem
import com.timeforpublic.domain.model.ServiceChecklist
import com.timeforpublic.domain.repository.DocumentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DocumentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : DocumentRepository {

    private val seedChecklists = listOf(
        ServiceChecklist(
            serviceId = "SRV-INC-01",
            serviceName = "Income Certificate (Tehsildar)",
            department = "Revenue Department",
            feeInr = 33,
            standardProcessingDays = 15,
            documents = listOf(
                DocumentItem("DOC-01", "Aadhaar Card", "UIDAI", isMandatory = true, guidanceNotes = "Original card required for biometric verification"),
                DocumentItem("DOC-02", "Ration Card (Original + Copy)", "Food & Civil Supplies", isMandatory = true, guidanceNotes = "Must list all family members living together"),
                DocumentItem("DOC-03", "Salary Slip / Form 16 / Income Affidavit", "Employer / Notary", isMandatory = true, guidanceNotes = "Latest 3 months if employed, notary affidavit if self-employed"),
                DocumentItem("DOC-04", "Talathi Income Report", "Local Talathi / Patwari", isMandatory = true, guidanceNotes = "Obtained from your local gram or ward administrative center"),
                DocumentItem("DOC-05", "Electricity Bill (Address Proof)", "Electricity Board", isMandatory = false, guidanceNotes = "Issued within the last 3 months")
            )
        ),
        ServiceChecklist(
            serviceId = "SRV-DL-02",
            serviceName = "Permanent Driving License (LMV)",
            department = "Transport Department (RTO)",
            feeInr = 200,
            standardProcessingDays = 7,
            documents = listOf(
                DocumentItem("DOC-11", "Valid Learner's License", "RTO / Parivahan", isMandatory = true, guidanceNotes = "Must be at least 30 days old and within 180 days"),
                DocumentItem("DOC-12", "Form 5 (Driving School Certificate)", "Authorized Motor Driving School", isMandatory = true, guidanceNotes = "Required for commercial and recommended for transport classes"),
                DocumentItem("DOC-13", "Slot Booking Appointment Receipt", "Parivahan Portal", isMandatory = true, guidanceNotes = "Printed appointment slip"),
                DocumentItem("DOC-14", "Valid Vehicle Registration & Insurance", "RTO / Insurer", isMandatory = true, guidanceNotes = "Test vehicle document copies")
            )
        ),
        ServiceChecklist(
            serviceId = "SRV-DOM-03",
            serviceName = "Domicile & Nationality Certificate",
            department = "Revenue Department",
            feeInr = 50,
            standardProcessingDays = 21,
            documents = listOf(
                DocumentItem("DOC-21", "Proof of Residence for 15+ Years", "Municipality / Gram Panchayat", isMandatory = true, guidanceNotes = "Continuous residential documents or utility bills"),
                DocumentItem("DOC-22", "School Leaving Certificate", "Education Dept / School", isMandatory = true, guidanceNotes = "Showing place of birth in the state"),
                DocumentItem("DOC-23", "Birth Certificate", "Municipal Corporation / Gram Panchayat", isMandatory = true, guidanceNotes = "Officially registered birth record"),
                DocumentItem("DOC-24", "Father's Domicile / School Certificate", "Education Dept", isMandatory = false, guidanceNotes = "Optional corroborating proof")
            )
        )
    )

    override fun getServiceChecklists(): Flow<Result<List<ServiceChecklist>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(seedChecklists))
            try {
                val response = apiService.getServiceChecklists()
                if (response.isSuccessful && response.body() != null) {
                    emit(Result.Success(response.body()!!))
                }
            } catch (_: Exception) {
                // Keep seed
            }
        } catch (e: Exception) {
            emit(Result.Error(AppError.Unknown(e.message ?: "Unable to fetch checklists", e)))
        }
    }

    override suspend fun getChecklistByServiceId(serviceId: String): Result<ServiceChecklist> {
        val checklist = seedChecklists.find { it.serviceId == serviceId }
        return if (checklist != null) Result.Success(checklist)
        else Result.Error(AppError.NotFound("Checklist for service $serviceId not found"))
    }

    override suspend fun checkEligibility(
        serviceId: String,
        userInputs: Map<String, Any>
    ): Result<Boolean> {
        return Result.Success(true)
    }
}
