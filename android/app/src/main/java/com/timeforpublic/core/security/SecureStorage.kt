package com.timeforpublic.core.security

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.timeforpublic.core.common.Constants
import com.timeforpublic.domain.model.UserRole
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Secure storage backed by Android Keystore via EncryptedSharedPreferences.
 * All sensitive session data (tokens, user identifiers) is encrypted at rest
 * using AES-256-GCM with a MasterKey stored in the hardware-backed Keystore.
 *
 * NEVER use plain SharedPreferences for tokens or credentials.
 */
@Singleton
class SecureStorage @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        Constants.ENCRYPTED_PREFS_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveUserSession(
        userId: String,
        phone: String,
        role: UserRole,
        officerId: String? = null
    ) {
        prefs.edit()
            .putString(Constants.KEY_USER_ID, userId)
            .putString(Constants.KEY_USER_PHONE, phone)
            .putString(Constants.KEY_USER_ROLE, role.name)
            .putString(Constants.KEY_OFFICER_ID, officerId)
            .apply()
    }

    fun getUserId(): String? = prefs.getString(Constants.KEY_USER_ID, null)

    fun getUserRole(): UserRole {
        val roleStr = prefs.getString(Constants.KEY_USER_ROLE, UserRole.CITIZEN.name)
        return try {
            UserRole.valueOf(roleStr ?: UserRole.CITIZEN.name)
        } catch (_: Exception) {
            UserRole.CITIZEN
        }
    }

    fun getUserPhone(): String? = prefs.getString(Constants.KEY_USER_PHONE, null)

    fun getOfficerId(): String? = prefs.getString(Constants.KEY_OFFICER_ID, null)

    fun hasActiveSession(): Boolean = !getUserId().isNullOrBlank()

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
