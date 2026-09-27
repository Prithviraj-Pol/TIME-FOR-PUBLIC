package com.timeforpublic.core.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Constants
import com.timeforpublic.core.common.Result
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Singleton
class GeofenceManager @Inject constructor(
    private val geofencingClient: GeofencingClient
) {

    /**
     * Registers an event-driven 200m circular geofence around the assigned Government Office.
     * ZERO continuous GPS polling — strictly triggers on entry/exit system events.
     */
    @SuppressLint("MissingPermission")
    suspend fun registerOfficeGeofence(
        context: Context,
        officeId: String,
        latitude: Double,
        longitude: Double,
        radiusMeters: Float = Constants.GEOFENCE_DEFAULT_RADIUS_METERS
    ): Result<Unit> {
        return try {
            val geofenceId = "${Constants.GEOFENCE_REQUEST_ID_PREFIX}$officeId"

            val geofence = Geofence.Builder()
                .setRequestId(geofenceId)
                .setCircularRegion(latitude, longitude, radiusMeters)
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_HOURS * 3600 * 1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .setLoiteringDelay(Constants.GEOFENCE_LOITERING_DELAY_MS)
                .build()

            val request = GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build()

            val pendingIntent = getGeofencePendingIntent(context)
            geofencingClient.addGeofences(request, pendingIntent).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(AppError.Unknown(e.message ?: "Failed to register geofence", e))
        }
    }

    suspend fun removeGeofence(context: Context): Result<Unit> {
        return try {
            val pendingIntent = getGeofencePendingIntent(context)
            geofencingClient.removeGeofences(pendingIntent).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(AppError.Unknown(e.message ?: "Failed to remove geofence", e))
        }
    }

    private fun getGeofencePendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java).apply {
            action = ACTION_GEOFENCE_EVENT
        }
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        return PendingIntent.getBroadcast(context, 0, intent, flags)
    }

    companion object {
        const val ACTION_GEOFENCE_EVENT = "com.timeforpublic.ACTION_GEOFENCE_EVENT"

        /**
         * Calculates distance in meters using the Haversine formula
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
}
