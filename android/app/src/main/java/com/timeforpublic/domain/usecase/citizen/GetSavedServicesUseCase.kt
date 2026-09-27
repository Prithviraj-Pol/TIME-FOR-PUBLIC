package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.repository.SavedServicesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedServicesUseCase @Inject constructor(
    private val savedServicesRepository: SavedServicesRepository
) {
    operator fun invoke(): Flow<List<GovernmentService>> {
        return savedServicesRepository.getSavedServices()
    }

    fun isSaved(serviceId: String): Flow<Boolean> {
        return savedServicesRepository.isSaved(serviceId)
    }
}
