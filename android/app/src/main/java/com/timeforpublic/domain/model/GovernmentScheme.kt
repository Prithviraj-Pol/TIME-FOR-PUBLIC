package com.timeforpublic.domain.model

data class GovernmentScheme(
    val id: String,
    val title: String,
    val department: String,
    val category: String,
    val description: String,
    val eligibility: List<String>,
    val benefits: String,
    val requiredDocuments: List<String>,
    val applicationUrl: String,
    val isCentralGovt: Boolean = true
)
