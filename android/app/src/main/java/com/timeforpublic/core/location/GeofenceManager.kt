package com.timeforpublic.core.location

import android.location.Location
import com.timeforpublic.core.common.Constants
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object GeofenceManager {

    /**
     * Calculates distance in meters between officer's current GPS location and office coordinates using Haversine formula
     */
    fun calculateDistanceMeters(
        currentLat: Double,
        currentLng: Double,
        officeLat: Double,
        officeLng: Double
    ): Float {
        val earthRadiusMeters = 6371000.0

        val dLat = Math.toRadians(officeLat - currentLat)
        val dLng = Math.toRadians(officeLng - currentLng)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(currentLat)) * cos(Math.toRadians(officeLat)) *
                sin(dLng / 2) * sin(dLng / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return (earthRadiusMeters * c).toFloat()
    }

    /**
     * Verifies if officer is currently within geofence perimeter of the target office
     */
    fun isWithinOfficeGeofence(
        currentLat: Double,
        currentLng: Double,
        officeLat: Double,
        officeLng: Double,
        radiusMeters: Float = Constants.GEOFENCE_DEFAULT_RADIUS_METERS
    ): Boolean {
        val distance = calculateDistanceMeters(currentLat, currentLng, officeLat, officeLng)
        return distance <= radiusMeters
    }
}
