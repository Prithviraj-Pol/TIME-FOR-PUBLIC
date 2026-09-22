package com.timeforpublic

import android.app.Application
import com.timeforpublic.core.notification.NotificationManager

class TimeForPublicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationManager.createNotificationChannels(this)
    }
}
