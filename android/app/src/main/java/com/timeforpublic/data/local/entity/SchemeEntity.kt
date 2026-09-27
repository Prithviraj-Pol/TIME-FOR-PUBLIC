package com.timeforpublic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timeforpublic.domain.model.GovernmentScheme

@Entity(tableName = "schemes")
data class SchemeEntity(
    @PrimaryKey val id: String,
    val title: String,
    val department: String,
    val category: String,
    val description: String,
    val eligibilityCriteria: List<String>,
    val benefits: String,
    val requiredDocuments: List<String>,
    val applicationUrl: String,
    val isCentralGovt: Boolean
) {
    fun toDomain(): GovernmentScheme = GovernmentScheme(
        id = id,
        title = title,
        department = department,
        category = category,
        description = description,
        eligibilityCriteria = eligibilityCriteria,
        benefits = benefits,
        requiredDocuments = requiredDocuments,
        applicationUrl = applicationUrl,
        isCentralGovt = isCentralGovt
    )

    companion object {
        fun fromDomain(scheme: GovernmentScheme): SchemeEntity = SchemeEntity(
            id = scheme.id,
            title = scheme.title,
            department = scheme.department,
            category = scheme.category,
            description = scheme.description,
            eligibilityCriteria = scheme.eligibilityCriteria,
            benefits = scheme.benefits,
            requiredDocuments = scheme.requiredDocuments,
            applicationUrl = scheme.applicationUrl,
            isCentralGovt = scheme.isCentralGovt
        )
    }
}
