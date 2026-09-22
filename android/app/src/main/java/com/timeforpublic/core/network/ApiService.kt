package com.timeforpublic.core.network

import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.model.GovernmentScheme
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.model.ServiceChecklist
import com.timeforpublic.domain.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginRequest(val phone: String, val role: String)
data class StatusUpdateRequest(
    val status: String,
    val note: String,
    val isGeofenceVerified: Boolean
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): User

    @GET("schemes")
    suspend fun getSchemes(@Query("category") category: String? = null): List<GovernmentScheme>

    @GET("schemes/{id}")
    suspend fun getSchemeById(@Path("id") id: String): GovernmentScheme

    @GET("offices")
    suspend fun getOffices(): List<GovernmentOffice>

    @GET("offices/{id}")
    suspend fun getOfficeById(@Path("id") id: String): GovernmentOffice

    @GET("officers/live")
    suspend fun getLiveOfficers(): List<OfficerStatus>

    @GET("officers/{id}")
    suspend fun getOfficerById(@Path("id") id: String): OfficerStatus

    @POST("officers/{id}/status")
    suspend fun updateOfficerStatus(
        @Path("id") id: String,
        @Body request: StatusUpdateRequest
    ): OfficerStatus

    @GET("documents/checklists")
    suspend fun getServiceChecklists(): List<ServiceChecklist>
}
