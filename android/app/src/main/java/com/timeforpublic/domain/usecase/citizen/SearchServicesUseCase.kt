package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.repository.ServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchServicesUseCase @Inject constructor(
    private val serviceRepository: ServiceRepository
) {
    operator fun invoke(query: String): Flow<Result<List<GovernmentService>>> {
        return serviceRepository.getServices(query = query)
    }
}
