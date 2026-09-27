package com.timeforpublic.domain.usecase.citizen

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.repository.OfficeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOfficesUseCase @Inject constructor(
    private val officeRepository: OfficeRepository
) {
    operator fun invoke(): Flow<Result<List<GovernmentOffice>>> {
        return officeRepository.getOffices()
    }

    suspend fun getById(id: String): Result<GovernmentOffice> {
        return officeRepository.getOfficeById(id)
    }
}
