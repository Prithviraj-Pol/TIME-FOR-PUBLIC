package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentScheme
import com.timeforpublic.domain.repository.SchemeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchemesUseCase @Inject constructor(
    private val schemeRepository: SchemeRepository
) {
    operator fun invoke(): Flow<Result<List<GovernmentScheme>>> {
        return schemeRepository.getSchemes()
    }

    suspend fun getById(id: String): Result<GovernmentScheme> {
        return schemeRepository.getSchemeById(id)
    }

    suspend fun search(query: String): Result<List<GovernmentScheme>> {
        return schemeRepository.searchSchemes(query)
    }

    suspend fun filterByCategory(category: String): Result<List<GovernmentScheme>> {
        return schemeRepository.filterByCategory(category)
    }
}
