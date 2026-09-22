package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentScheme
import kotlinx.coroutines.flow.Flow

interface SchemeRepository {
    fun getSchemes(): Flow<Result<List<GovernmentScheme>>>
    suspend fun getSchemeById(id: String): Result<GovernmentScheme>
    suspend fun searchSchemes(query: String): Result<List<GovernmentScheme>>
    suspend fun filterByCategory(category: String): Result<List<GovernmentScheme>>
}
