package com.timeforpublic.core.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.timeforpublic.core.common.Constants
import com.timeforpublic.core.notification.NotificationManager
import com.timeforpublic.core.security.SecureStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var secureStorage: SecureStorage

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent) ?: return

        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        val triggeringGeofences = geofencingEvent.triggeringGeofences ?: return

        for (geofence in triggeringGeofences) {
            val requestId = geofence.requestId
            val officeId = requestId.removePrefix(Constants.GEOFENCE_REQUEST_ID_PREFIX)

            when (geofenceTransition) {
                Geofence.GEOFENCE_TRANSITION_ENTER -> {
                    // Officer entered the office perimeter (200m)
                    NotificationManager.showNotification(
                        context = context,
                        channelId = Constants.CHANNEL_OFFICER_STATUS,
                        notificationId = 1001,
                        title = "Office Perimeter Detected",
                        message = "You have entered your designated office. Status set to In Office."
                    )
                }
                Geofence.GEOFENCE_TRANSITION_EXIT -> {
                    // Officer exited the office perimeter
                    NotificationManager.showNotification(
                        context = context,
                        channelId = Constants.CHANNEL_OFFICER_STATUS,
                        notificationId = 1002,
                        title = "Office Perimeter Exited",
                        message = "You have left your designated office. Verification updated."
                    )
                }
            }
        }
    }
}
