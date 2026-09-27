package com.timeforpublic.data.repository

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import com.timeforpublic.data.local.dao.SavedItemDao
import com.timeforpublic.data.local.dao.ServiceDao
import com.timeforpublic.data.local.entity.SavedItemEntity
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.ServiceCategory
import com.timeforpublic.domain.repository.SavedServicesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavedServicesRepositoryImpl @Inject constructor(
    private val savedItemDao: SavedItemDao,
    private val serviceDao: ServiceDao
) : SavedServicesRepository {

    override fun getSavedServices(): Flow<List<GovernmentService>> {
        return savedItemDao.getAllSavedItems().map { items ->
            items.map { item ->
                GovernmentService(
                    id = item.id,
                    title = item.title,
                    category = try { ServiceCategory.valueOf(item.category) } catch (_: Exception) { ServiceCategory.GENERAL },
                    department = item.department,
                    description = item.description,
                    eligibilitySummary = item.eligibilitySummary,
                    officialPortalUrl = item.officialPortalUrl,
                    estimatedProcessingDays = item.estimatedProcessingDays,
                    isSaved = true
                )
            }
        }
    }

    override suspend fun toggleSaved(service: GovernmentService): Result<Boolean> {
        return try {
            val isCurrentlySaved = savedItemDao.isItemSaved(service.id).first()
            if (isCurrentlySaved) {
                savedItemDao.deleteSavedItem(service.id)
                serviceDao.updateSavedStatus(service.id, false)
                Result.Success(false)
            } else {
                val entity = SavedItemEntity(
                    id = service.id,
                    title = service.title,
                    category = service.category.name,
                    department = service.department,
                    description = service.description,
                    eligibilitySummary = service.eligibilitySummary,
                    officialPortalUrl = service.officialPortalUrl,
                    estimatedProcessingDays = service.estimatedProcessingDays
                )
                savedItemDao.insertSavedItem(entity)
                serviceDao.updateSavedStatus(service.id, true)
                Result.Success(true)
            }
        } catch (e: Exception) {
            Result.Error(AppError.Unknown(e.message ?: "Failed to update saved service", e))
        }
    }

    override fun isSaved(serviceId: String): Flow<Boolean> {
        return savedItemDao.isItemSaved(serviceId)
    }
}
