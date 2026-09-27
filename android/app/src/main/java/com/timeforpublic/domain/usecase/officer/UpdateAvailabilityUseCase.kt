package com.timeforpublic.domain.usecase.officer

import com.timeforpublic.core.common.Result
import com.timeforpublic.domain.model.AvailabilityStatus
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.repository.OfficerRepository
import javax.inject.Inject

class UpdateAvailabilityUseCase @Inject constructor(
    private val officerRepository: OfficerRepository
) {
    suspend operator fun invoke(
        officerId: String,
        status: AvailabilityStatus,
        note: String = "",
        isGeofenceVerified: Boolean = false
    ): Result<OfficerStatus> {
        return officerRepository.updateAvailability(
            officerId = officerId,
            status = status,
            note = note,
            isGeofenceVerified = isGeofenceVerified
        )
    }
}
