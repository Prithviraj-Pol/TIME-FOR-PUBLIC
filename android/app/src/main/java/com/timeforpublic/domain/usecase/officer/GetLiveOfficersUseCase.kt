package com.timeforpublic.domain.usecase.officer

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.repository.OfficerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLiveOfficersUseCase @Inject constructor(
    private val officerRepository: OfficerRepository
) {
    operator fun invoke(): Flow<Result<List<OfficerStatus>>> {
        return officerRepository.getLiveOfficers()
    }

    suspend fun getById(officerId: String): Result<OfficerStatus> {
        return officerRepository.getOfficerById(officerId)
    }

    fun getByOffice(officeId: String): Flow<Result<List<OfficerStatus>>> {
        return officerRepository.getOfficersByOffice(officeId)
    }
}
