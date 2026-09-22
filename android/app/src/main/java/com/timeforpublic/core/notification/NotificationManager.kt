package com.timeforpublic.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager as AndroidNotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.timeforpublic.core.common.Constants

object NotificationManager {

    fun createNotificationChannels(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager

            val officerChannel = NotificationChannel(
                Constants.CHANNEL_OFFICER_STATUS,
                "Officer Availability Updates",
                AndroidNotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifications when officers become available or go on duty"
            }

            val schemeChannel = NotificationChannel(
                Constants.CHANNEL_SCHEME_ALERTS,
                "Government Scheme Alerts",
                AndroidNotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Updates on newly launched or closing citizen schemes"
            }

            val generalChannel = NotificationChannel(
                Constants.CHANNEL_GENERAL,
                "General Notices",
                AndroidNotificationManager.IMPORTANCE_LOW
            )

            notificationManager.createNotificationChannels(
                listOf(officerChannel, schemeChannel, generalChannel)
            )
        }
    }

    fun showNotification(
        context: Context,
        channelId: String,
        notificationId: Int,
        title: String,
        message: String
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as AndroidNotificationManager

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}
