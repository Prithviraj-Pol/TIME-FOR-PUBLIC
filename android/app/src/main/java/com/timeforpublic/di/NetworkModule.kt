package com.timeforpublic.di

import com.timeforpublic.core.network.ApiClient
import com.timeforpublic.core.network.ApiService

object NetworkModule {
    val apiService: ApiService get() = ApiClient.apiService
}
