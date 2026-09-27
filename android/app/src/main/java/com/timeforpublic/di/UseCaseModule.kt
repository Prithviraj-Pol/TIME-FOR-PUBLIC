package com.timeforpublic.di

import com.timeforpublic.domain.repository.AiAssistantRepository
import com.timeforpublic.domain.repository.AuthRepository
import com.timeforpublic.domain.repository.DocumentRepository
import com.timeforpublic.domain.repository.OfficeRepository
import com.timeforpublic.domain.repository.OfficerRepository
import com.timeforpublic.domain.repository.SavedServicesRepository
import com.timeforpublic.domain.repository.SchemeRepository
import com.timeforpublic.domain.repository.ServiceRepository
import com.timeforpublic.domain.usecase.ai.QueryAiGuidanceUseCase
import com.timeforpublic.domain.usecase.auth.GetCurrentUserUseCase
import com.timeforpublic.domain.usecase.auth.LoginUseCase
import com.timeforpublic.domain.usecase.auth.LogoutUseCase
import com.timeforpublic.domain.usecase.citizen.GetOfficesUseCase
import com.timeforpublic.domain.usecase.citizen.GetSavedServicesUseCase
import com.timeforpublic.domain.usecase.citizen.GetSchemesUseCase
import com.timeforpublic.domain.usecase.citizen.GetServicesUseCase
import com.timeforpublic.domain.usecase.citizen.SearchServicesUseCase
import com.timeforpublic.domain.usecase.citizen.ToggleSavedServiceUseCase
import com.timeforpublic.domain.usecase.documents.GetChecklistUseCase
import com.timeforpublic.domain.usecase.documents.VerifyDocumentEligibilityUseCase
import com.timeforpublic.domain.usecase.officer.GetLiveOfficersUseCase
import com.timeforpublic.domain.usecase.officer.UpdateAvailabilityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(repo: AuthRepository): LoginUseCase = LoginUseCase(repo)

    @Provides
    @Singleton
    fun provideLogoutUseCase(repo: AuthRepository): LogoutUseCase = LogoutUseCase(repo)

    @Provides
    @Singleton
    fun provideGetCurrentUserUseCase(repo: AuthRepository): GetCurrentUserUseCase = GetCurrentUserUseCase(repo)

    @Provides
    @Singleton
    fun provideGetServicesUseCase(repo: ServiceRepository): GetServicesUseCase = GetServicesUseCase(repo)

    @Provides
    @Singleton
    fun provideSearchServicesUseCase(repo: ServiceRepository): SearchServicesUseCase = SearchServicesUseCase(repo)

    @Provides
    @Singleton
    fun provideGetSchemesUseCase(repo: SchemeRepository): GetSchemesUseCase = GetSchemesUseCase(repo)

    @Provides
    @Singleton
    fun provideGetOfficesUseCase(repo: OfficeRepository): GetOfficesUseCase = GetOfficesUseCase(repo)

    @Provides
    @Singleton
    fun provideGetSavedServicesUseCase(repo: SavedServicesRepository): GetSavedServicesUseCase = GetSavedServicesUseCase(repo)

    @Provides
    @Singleton
    fun provideToggleSavedServiceUseCase(repo: SavedServicesRepository): ToggleSavedServiceUseCase = ToggleSavedServiceUseCase(repo)

    @Provides
    @Singleton
    fun provideGetChecklistUseCase(repo: DocumentRepository): GetChecklistUseCase = GetChecklistUseCase(repo)

    @Provides
    @Singleton
    fun provideVerifyDocumentEligibilityUseCase(repo: DocumentRepository): VerifyDocumentEligibilityUseCase = VerifyDocumentEligibilityUseCase(repo)

    @Provides
    @Singleton
    fun provideGetLiveOfficersUseCase(repo: OfficerRepository): GetLiveOfficersUseCase = GetLiveOfficersUseCase(repo)

    @Provides
    @Singleton
    fun provideUpdateAvailabilityUseCase(repo: OfficerRepository): UpdateAvailabilityUseCase = UpdateAvailabilityUseCase(repo)

    @Provides
    @Singleton
    fun provideQueryAiGuidanceUseCase(repo: AiAssistantRepository): QueryAiGuidanceUseCase = QueryAiGuidanceUseCase(repo)
}
