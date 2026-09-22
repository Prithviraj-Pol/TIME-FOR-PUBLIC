package com.timeforpublic.di

import android.content.Context
import com.timeforpublic.data.repository.AuthRepositoryImpl
import com.timeforpublic.data.repository.DocumentRepositoryImpl
import com.timeforpublic.data.repository.OfficeRepositoryImpl
import com.timeforpublic.data.repository.OfficerRepositoryImpl
import com.timeforpublic.data.repository.SchemeRepositoryImpl
import com.timeforpublic.domain.repository.AuthRepository
import com.timeforpublic.domain.repository.DocumentRepository
import com.timeforpublic.domain.repository.OfficeRepository
import com.timeforpublic.domain.repository.OfficerRepository
import com.timeforpublic.domain.repository.SchemeRepository

object RepositoryModule {
    @Volatile
    private var authRepo: AuthRepository? = null
    @Volatile
    private var schemeRepo: SchemeRepository? = null
    @Volatile
    private var officeRepo: OfficeRepository? = null
    @Volatile
    private var officerRepo: OfficerRepository? = null
    @Volatile
    private var documentRepo: DocumentRepository? = null

    fun provideAuthRepository(context: Context): AuthRepository {
        return authRepo ?: synchronized(this) {
            authRepo ?: AuthRepositoryImpl(
                NetworkModule.apiService,
                DatabaseModule.provideSecureStorage(context.applicationContext),
                DatabaseModule.provideTokenManager(context.applicationContext)
            ).also { authRepo = it }
        }
    }

    fun provideSchemeRepository(): SchemeRepository {
        return schemeRepo ?: synchronized(this) {
            schemeRepo ?: SchemeRepositoryImpl(NetworkModule.apiService).also { schemeRepo = it }
        }
    }

    fun provideOfficeRepository(): OfficeRepository {
        return officeRepo ?: synchronized(this) {
            officeRepo ?: OfficeRepositoryImpl(NetworkModule.apiService).also { officeRepo = it }
        }
    }

    fun provideOfficerRepository(): OfficerRepository {
        return officerRepo ?: synchronized(this) {
            officerRepo ?: OfficerRepositoryImpl(NetworkModule.apiService).also { officerRepo = it }
        }
    }

    fun provideDocumentRepository(): DocumentRepository {
        return documentRepo ?: synchronized(this) {
            documentRepo ?: DocumentRepositoryImpl(NetworkModule.apiService).also { documentRepo = it }
        }
    }
}
