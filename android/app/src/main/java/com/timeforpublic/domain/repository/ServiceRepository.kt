package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.ServiceCategory
import kotlinx.coroutines.flow.Flow

interface ServiceRepository {
    fun getServices(category: ServiceCategory? = null, query: String? = null): Flow<Result<List<GovernmentService>>>
    fun getServiceById(id: String): Flow<Result<GovernmentService>>
}
