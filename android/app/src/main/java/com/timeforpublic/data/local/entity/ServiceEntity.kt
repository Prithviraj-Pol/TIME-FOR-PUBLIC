package com.timeforpublic.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.ServiceCategory

@Entity(tableName = "services")
data class ServiceEntity(
    @PrimaryKey val id: String,
    val title: String,
    val category: String,
    val department: String,
    val description: String,
    val eligibilitySummary: String,
    val requiredDocumentsSummary: List<String>,
    val officialPortalUrl: String,
    val estimatedProcessingDays: Int,
    val isSaved: Boolean = false
) {
    fun toDomain(): GovernmentService = GovernmentService(
        id = id,
        title = title,
        category = try { ServiceCategory.valueOf(category) } catch (_: Exception) { ServiceCategory.GENERAL },
        department = department,
        description = description,
        eligibilitySummary = eligibilitySummary,
        requiredDocumentsSummary = requiredDocumentsSummary,
        officialPortalUrl = officialPortalUrl,
        estimatedProcessingDays = estimatedProcessingDays,
        isSaved = isSaved
    )

    companion object {
        fun fromDomain(service: GovernmentService): ServiceEntity = ServiceEntity(
            id = service.id,
            title = service.title,
            category = service.category.name,
            department = service.department,
            description = service.description,
            eligibilitySummary = service.eligibilitySummary,
            requiredDocumentsSummary = service.requiredDocumentsSummary,
            officialPortalUrl = service.officialPortalUrl,
            estimatedProcessingDays = service.estimatedProcessingDays,
            isSaved = service.isSaved
        )
    }
}
