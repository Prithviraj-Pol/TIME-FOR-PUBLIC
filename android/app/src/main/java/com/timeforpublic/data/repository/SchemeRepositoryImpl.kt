package com.timeforpublic.data.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.core.network.ApiService
import com.timeforpublic.domain.model.GovernmentScheme
import com.timeforpublic.domain.repository.SchemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SchemeRepositoryImpl(
    private val apiService: ApiService
) : SchemeRepository {

    private val seedSchemes = listOf(
        GovernmentScheme(
            id = "SCH-001",
            title = "Pradhan Mantri Awas Yojana (PMAY)",
            department = "Ministry of Housing and Urban Affairs",
            category = "Housing & Infrastructure",
            description = "Interest subsidy and financial assistance for pucca house construction for urban and rural families.",
            eligibility = listOf("No pucca house anywhere in India", "Annual family income up to ₹18 Lakhs", "Female head of family co-ownership"),
            benefits = "Subsidy up to ₹2.67 Lakhs on home loan interest",
            requiredDocuments = listOf("Aadhaar Card", "Income Certificate", "Bank Account Statement", "Affidavit of No Pucca House"),
            applicationUrl = "https://pmaymis.gov.in"
        ),
        GovernmentScheme(
            id = "SCH-002",
            title = "Ayushman Bharat - PM-JAY",
            department = "National Health Authority",
            category = "Health & Wellness",
            description = "Health insurance coverage up to ₹5 Lakhs per family per year for secondary and tertiary care hospitalization.",
            eligibility = listOf("Identified in SECC 2011 database", "Rural households with D1-D7 deprivation criteria", "Active Ration Card holders"),
            benefits = "Cashless medical treatment up to ₹5,00,000 annually",
            requiredDocuments = listOf("Aadhaar Card", "Ration Card", "PM-JAY Family ID / Letter"),
            applicationUrl = "https://pmjay.gov.in"
        ),
        GovernmentScheme(
            id = "SCH-003",
            title = "PM Kisan Samman Nidhi",
            department = "Ministry of Agriculture & Farmers Welfare",
            category = "Agriculture & Rural",
            description = "Direct income support of ₹6,000 per year in three equal installments to all landholding farmer families.",
            eligibility = listOf("Landholding farmer family", "Names registered in state land revenue records", "Aadhaar linked bank account"),
            benefits = "₹6,000 per year directly transferred to bank account",
            requiredDocuments = listOf("Aadhaar Card", "7/12 Land Record / Khatauni", "Bank Passbook"),
            applicationUrl = "https://pmkisan.gov.in"
        ),
        GovernmentScheme(
            id = "SCH-004",
            title = "Digital Driving License & Learning License",
            department = "Ministry of Road Transport and Highways (MoRTH)",
            category = "Transport & Mobility",
            description = "Online application, slot booking, and contactless learner license test under Parivahan Sewa.",
            eligibility = listOf("Age 18+ for motor vehicle with gear", "Age 16+ for gearless scooter <50cc", "Resident of district jurisdiction"),
            benefits = "Immediate digital issuance via DigiLocker",
            requiredDocuments = listOf("Age Proof (10th Marksheet/Birth Certificate)", "Address Proof (Aadhaar/Voter ID)", "Medical Certificate Form 1A"),
            applicationUrl = "https://parivahan.gov.in"
        )
    )

    override fun getSchemes(): Flow<Result<List<GovernmentScheme>>> = flow {
        emit(Result.Loading)
        try {
            emit(Result.Success(seedSchemes))
        } catch (e: Exception) {
            emit(Result.Error("Unable to fetch schemes", e))
        }
    }

    override suspend fun getSchemeById(id: String): Result<GovernmentScheme> {
        val scheme = seedSchemes.find { it.id == id }
        return if (scheme != null) Result.Success(scheme)
        else Result.Error("Scheme with ID $id not found")
    }

    override suspend fun searchSchemes(query: String): Result<List<GovernmentScheme>> {
        val filtered = seedSchemes.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.department.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true)
        }
        return Result.Success(filtered)
    }

    override suspend fun filterByCategory(category: String): Result<List<GovernmentScheme>> {
        val filtered = seedSchemes.filter { it.category.equals(category, ignoreCase = true) }
        return Result.Success(filtered)
    }
}
