package com.timeforpublic.domain.usecase.officer

import android.content.Context
import com.timeforpublic.core.common.Result
import com.timeforpublic.core.location.GeofenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RegisterGeofenceUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val geofenceManager: GeofenceManager
) {
    suspend operator fun invoke(
        officeId: String,
        latitude: Double,
        longitude: Double,
        radiusMeters: Float = 200f
    ): Result<Unit> {
        return geofenceManager.registerOfficeGeofence(
            context = context,
            officeId = officeId,
            latitude = latitude,
            longitude = longitude,
            radiusMeters = radiusMeters
        )
    }
}
