package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.ServiceCategory
import com.timeforpublic.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetServicesUseCase @Inject constructor(
    private val serviceRepository: ServiceRepository
) {
    operator fun invoke(category: ServiceCategory? = null): Flow<Result<List<GovernmentService>>> {
        return serviceRepository.getServices(category = category)
    }

    fun getById(id: String): Flow<Result<GovernmentService>> {
        return serviceRepository.getServiceById(id)
    }
}
