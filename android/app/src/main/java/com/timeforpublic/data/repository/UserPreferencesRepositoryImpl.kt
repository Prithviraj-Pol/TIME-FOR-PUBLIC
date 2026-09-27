package com.timeforpublic.data.repository

import com.timeforpublic.core.datastore.PreferencesDataSource
import com.timeforpublic.domain.model.AppTheme
import com.timeforpublic.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : UserPreferencesRepository {

    override fun getThemePreference(): Flow<AppTheme> = preferencesDataSource.themePreference

    override suspend fun setThemePreference(theme: AppTheme) {
        preferencesDataSource.setThemePreference(theme)
    }

    override fun isOnboardingCompleted(): Flow<Boolean> = preferencesDataSource.isOnboardingCompleted

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        preferencesDataSource.setOnboardingCompleted(completed)
    }
}
