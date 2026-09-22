package com.timeforpublic.core.common

object Constants {
    const val BASE_URL = "https://api.timeforpublic.gov.in/v1/"
    const val TIMEOUT_SECONDS = 30L

    // Preferences & Secure Storage Keys
    const val PREFS_NAME = "time_for_public_prefs"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_USER_ROLE = "user_role"
    const val KEY_USER_PHONE = "user_phone"
    const val KEY_OFFICER_ID = "officer_id"

    // Geofencing constants
    const val GEOFENCE_DEFAULT_RADIUS_METERS = 100.0f
    const val GEOFENCE_EXPIRATION_HOURS = 12L

    // Notification Channel IDs
    const val CHANNEL_OFFICER_STATUS = "channel_officer_status"
    const val CHANNEL_SCHEME_ALERTS = "channel_scheme_alerts"
    const val CHANNEL_GENERAL = "channel_general"
}
