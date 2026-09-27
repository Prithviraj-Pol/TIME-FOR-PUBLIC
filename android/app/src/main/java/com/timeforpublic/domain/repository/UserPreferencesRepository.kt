package com.timeforpublic.domain.repository

import com.timeforpublic.domain.model.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun getThemePreference(): Flow<AppTheme>
    suspend fun setThemePreference(theme: AppTheme)
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)
}
