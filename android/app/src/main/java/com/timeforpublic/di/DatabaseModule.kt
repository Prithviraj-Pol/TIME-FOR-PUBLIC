package com.timeforpublic.di

import android.content.Context
import com.timeforpublic.core.security.SecureStorage
import com.timeforpublic.core.security.TokenManager

object DatabaseModule {
    fun provideSecureStorage(context: Context): SecureStorage = SecureStorage(context)
    fun provideTokenManager(context: Context): TokenManager = TokenManager(context)
}
