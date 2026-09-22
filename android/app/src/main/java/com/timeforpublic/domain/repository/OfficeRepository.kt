package com.timeforpublic.domain.repository

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentOffice
import kotlinx.coroutines.flow.Flow

interface OfficeRepository {
    fun getOffices(): Flow<Result<List<GovernmentOffice>>>
    suspend fun getOfficeById(id: String): Result<GovernmentOffice>
    suspend fun getOfficesByDepartment(department: String): Result<List<GovernmentOffice>>
}
