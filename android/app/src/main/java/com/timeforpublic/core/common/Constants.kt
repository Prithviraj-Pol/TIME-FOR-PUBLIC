package com.timeforpublic.core.common

/**
 * Application-wide constants.
 * API base URL is sourced from BuildConfig (build-variant specific).
 * No secrets or private keys are stored here.
 */
object Constants {
    const val TIMEOUT_SECONDS = 30L

    // Preferences & Secure Storage Keys
    const val PREFS_NAME = "time_for_public_prefs"
    const val ENCRYPTED_PREFS_NAME = "time_for_public_secure_prefs"
    const val KEY_AUTH_TOKEN = "auth_token"
    const val KEY_REFRESH_TOKEN = "refresh_token"
    const val KEY_USER_ROLE = "user_role"
    const val KEY_USER_PHONE = "user_phone"
    const val KEY_USER_ID = "user_id"
    const val KEY_OFFICER_ID = "officer_id"
    const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    // Geofencing constants (200m per architectural specification)
    const val GEOFENCE_DEFAULT_RADIUS_METERS = 200f
    const val GEOFENCE_EXPIRATION_HOURS = 12L
    const val GEOFENCE_LOITERING_DELAY_MS = 30_000 // 30 seconds
    const val GEOFENCE_REQUEST_ID_PREFIX = "tfp_office_"

    // Notification Channel IDs
    const val CHANNEL_OFFICER_STATUS = "channel_officer_status"
    const val CHANNEL_SCHEME_ALERTS = "channel_scheme_alerts"
    const val CHANNEL_GENERAL = "channel_general"

    // DataStore
    const val DATASTORE_USER_PREFERENCES = "user_preferences"

    // Room Database
    const val DATABASE_NAME = "time_for_public_db"
}
