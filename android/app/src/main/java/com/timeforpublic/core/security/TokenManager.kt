package com.timeforpublic.core.security

import android.content.Context
import android.content.SharedPreferences
import com.timeforpublic.core.common.Constants

class TokenManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        prefs.edit().putString(Constants.KEY_AUTH_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return prefs.getString(Constants.KEY_AUTH_TOKEN, null)
    }

    fun clearToken() {
        prefs.edit().remove(Constants.KEY_AUTH_TOKEN).apply()
    }

    fun hasToken(): Boolean = !getToken().isNullOrBlank()
}
