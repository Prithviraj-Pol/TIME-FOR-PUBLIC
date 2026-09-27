package com.timeforpublic

import android.app.Application
import com.timeforpublic.core.notification.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TimeForPublicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationManager.createNotificationChannels(this)
    }
}
