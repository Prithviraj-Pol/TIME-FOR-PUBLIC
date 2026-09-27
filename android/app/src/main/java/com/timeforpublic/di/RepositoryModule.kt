package com.timeforpublic.di

import com.timeforpublic.data.repository.AiAssistantRepositoryImpl
import com.timeforpublic.data.repository.AuthRepositoryImpl
import com.timeforpublic.data.repository.DocumentRepositoryImpl
import com.timeforpublic.data.repository.OfficeRepositoryImpl
import com.timeforpublic.data.repository.OfficerRepositoryImpl
import com.timeforpublic.data.repository.SavedServicesRepositoryImpl
import com.timeforpublic.data.repository.SchemeRepositoryImpl
import com.timeforpublic.data.repository.ServiceRepositoryImpl
import com.timeforpublic.data.repository.UserPreferencesRepositoryImpl
import com.timeforpublic.domain.repository.AiAssistantRepository
import com.timeforpublic.domain.repository.AuthRepository
import com.timeforpublic.domain.repository.DocumentRepository
import com.timeforpublic.domain.repository.OfficeRepository
import com.timeforpublic.domain.repository.OfficerRepository
import com.timeforpublic.domain.repository.SavedServicesRepository
import com.timeforpublic.domain.repository.SchemeRepository
import com.timeforpublic.domain.repository.ServiceRepository
import com.timeforpublic.domain.repository.UserPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindServiceRepository(
        impl: ServiceRepositoryImpl
    ): ServiceRepository

    @Binds
    @Singleton
    abstract fun bindSchemeRepository(
        impl: SchemeRepositoryImpl
    ): SchemeRepository

    @Binds
    @Singleton
    abstract fun bindOfficeRepository(
        impl: OfficeRepositoryImpl
    ): OfficeRepository

    @Binds
    @Singleton
    abstract fun bindOfficerRepository(
        impl: OfficerRepositoryImpl
    ): OfficerRepository

    @Binds
    @Singleton
    abstract fun bindDocumentRepository(
        impl: DocumentRepositoryImpl
    ): DocumentRepository

    @Binds
    @Singleton
    abstract fun bindAiAssistantRepository(
        impl: AiAssistantRepositoryImpl
    ): AiAssistantRepository

    @Binds
    @Singleton
    abstract fun bindSavedServicesRepository(
        impl: SavedServicesRepositoryImpl
    ): SavedServicesRepository

    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
}
