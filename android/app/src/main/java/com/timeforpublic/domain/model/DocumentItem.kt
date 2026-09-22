package com.timeforpublic.domain.model

data class DocumentItem(
    val id: String,
    val name: String,
    val issuingAuthority: String,
    val isMandatory: Boolean = true,
    val acceptedFormats: List<String> = listOf("Original", "Self-Attested Photocopy", "DigiLocker"),
    val description: String = "",
    val validityPeriod: String = "Permanent",
    val estimatedDaysToObtain: Int = 3
)

data class ServiceChecklist(
    val serviceId: String,
    val serviceName: String,
    val department: String,
    val feeInr: Int = 0,
    val standardProcessingDays: Int = 15,
    val documents: List<DocumentItem>
)
