package com.timeforpublic.core.security

import android.content.Context
import android.content.SharedPreferences
import com.timeforpublic.core.common.Constants
import com.timeforpublic.domain.model.UserRole

class SecureStorage(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserSession(phone: String, role: UserRole, officerId: String? = null) {
        prefs.edit()
            .putString(Constants.KEY_USER_PHONE, phone)
            .putString(Constants.KEY_USER_ROLE, role.name)
            .putString(Constants.KEY_OFFICER_ID, officerId)
            .apply()
    }

    fun getUserRole(): UserRole {
        val roleStr = prefs.getString(Constants.KEY_USER_ROLE, UserRole.CITIZEN.name)
        return try {
            UserRole.valueOf(roleStr ?: UserRole.CITIZEN.name)
        } catch (e: Exception) {
            UserRole.CITIZEN
        }
    }

    fun getUserPhone(): String? = prefs.getString(Constants.KEY_USER_PHONE, null)

    fun getOfficerId(): String? = prefs.getString(Constants.KEY_OFFICER_ID, null)

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
