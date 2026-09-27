package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.repository.SavedServicesRepository
import javax.inject.Inject

class ToggleSavedServiceUseCase @Inject constructor(
    private val savedServicesRepository: SavedServicesRepository
) {
    suspend operator fun invoke(service: GovernmentService): Result<Boolean> {
        return savedServicesRepository.toggleSaved(service)
    }
}
