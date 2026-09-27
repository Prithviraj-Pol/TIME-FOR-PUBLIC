package com.timeforpublic.core.network

import com.timeforpublic.domain.model.AiGuidanceResponse
import com.timeforpublic.domain.model.GovernmentOffice
import com.timeforpublic.domain.model.GovernmentScheme
import com.timeforpublic.domain.model.GovernmentService
import com.timeforpublic.domain.model.OfficerStatus
import com.timeforpublic.domain.model.ServiceChecklist
import com.timeforpublic.domain.model.User
import com.timeforpublic.core.network.dto.LoginRequest
import com.timeforpublic.core.network.dto.LoginResponse
import com.timeforpublic.core.network.dto.StatusUpdateRequest
import com.timeforpublic.core.network.dto.AiQueryRequest
import com.timeforpublic.core.network.dto.GeofenceEventRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit API service interface.
 * All endpoints communicate via HTTPS to the FastAPI backend.
 * The Android client never accesses LLM providers, databases,
 * or Firebase directly for sensitive operations.
 */
interface ApiService {

    // ── Authentication ──

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    // ── Government Services ──

    @GET("services")
    suspend fun getServices(
        @Query("category") category: String? = null,
        @Query("query") query: String? = null
    ): Response<List<GovernmentService>>

    @GET("services/{id}")
    suspend fun getServiceById(@Path("id") id: String): Response<GovernmentService>

    // ── Schemes ──

    @GET("schemes")
    suspend fun getSchemes(
        @Query("category") category: String? = null
    ): Response<List<GovernmentScheme>>

    @GET("schemes/{id}")
    suspend fun getSchemeById(@Path("id") id: String): Response<GovernmentScheme>

    // ── Offices ──

    @GET("offices")
    suspend fun getOffices(): Response<List<GovernmentOffice>>

    @GET("offices/{id}")
    suspend fun getOfficeById(@Path("id") id: String): Response<GovernmentOffice>

    // ── Officers (Live Availability) ──

    @GET("officers/live")
    suspend fun getLiveOfficers(): Response<List<OfficerStatus>>

    @GET("officers/{id}")
    suspend fun getOfficerById(@Path("id") id: String): Response<OfficerStatus>

    @PUT("officers/{id}/status")
    suspend fun updateOfficerStatus(
        @Path("id") id: String,
        @Body request: StatusUpdateRequest
    ): Response<OfficerStatus>

    // ── Geofence Events ──

    @POST("officers/{id}/geofence-event")
    suspend fun reportGeofenceEvent(
        @Path("id") id: String,
        @Body request: GeofenceEventRequest
    ): Response<Unit>

    // ── Document Checklists ──

    @GET("documents/checklists")
    suspend fun getServiceChecklists(): Response<List<ServiceChecklist>>

    @GET("documents/checklists/{serviceId}")
    suspend fun getChecklistByServiceId(
        @Path("serviceId") serviceId: String
    ): Response<ServiceChecklist>

    // ── AI Assistant ──

    @POST("ai/query")
    suspend fun queryAiAssistant(
        @Body request: AiQueryRequest
    ): Response<AiGuidanceResponse>
}
