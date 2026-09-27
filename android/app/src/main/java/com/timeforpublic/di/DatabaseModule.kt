package com.timeforpublic.di

import android.content.Context
import androidx.room.Room
import com.timeforpublic.core.common.Constants
import com.timeforpublic.core.database.AppDatabase
import com.timeforpublic.data.local.dao.OfficeDao
import com.timeforpublic.data.local.dao.SavedItemDao
import com.timeforpublic.data.local.dao.SchemeDao
import com.timeforpublic.data.local.dao.ServiceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideServiceDao(database: AppDatabase): ServiceDao = database.serviceDao()

    @Provides
    fun provideSchemeDao(database: AppDatabase): SchemeDao = database.schemeDao()

    @Provides
    fun provideOfficeDao(database: AppDatabase): OfficeDao = database.officeDao()

    @Provides
    fun provideSavedItemDao(database: AppDatabase): SavedItemDao = database.savedItemDao()
}
