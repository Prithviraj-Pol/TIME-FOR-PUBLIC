package com.timeforpublic.di

import android.content.Context

class AppModule(val context: Context) {
    val authRepository = RepositoryModule.provideAuthRepository(context)
    val schemeRepository = RepositoryModule.provideSchemeRepository()
    val officeRepository = RepositoryModule.provideOfficeRepository()
    val officerRepository = RepositoryModule.provideOfficerRepository()
    val documentRepository = RepositoryModule.provideDocumentRepository()
}
