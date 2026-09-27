package com.timeforpublic.core.network

import com.timeforpublic.core.common.AppError
import com.timeforpublic.core.common.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Safe network call wrapper that maps HTTP errors and exceptions
 * to structured AppError types.
 * Every repository should use this instead of calling API methods directly.
 */
suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): Result<T> {
    return withContext(dispatcher) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error(AppError.Unknown("Empty response body"))
                }
            } else {
                Result.Error(mapHttpError(response.code(), response.message()))
            }
        } catch (e: SocketTimeoutException) {
            Result.Error(AppError.Timeout(cause = e))
        } catch (e: UnknownHostException) {
            Result.Error(AppError.NetworkError(cause = e))
        } catch (e: IOException) {
            Result.Error(AppError.NetworkError(
                message = "Connection failed. Please check your network.",
                cause = e
            ))
        } catch (e: Exception) {
            Result.Error(AppError.Unknown(
                message = e.localizedMessage ?: "An unexpected error occurred.",
                cause = e
            ))
        }
    }
}

/**
 * Maps HTTP status codes to structured AppError types.
 */
private fun mapHttpError(code: Int, message: String): AppError {
    return when (code) {
        401 -> AppError.Unauthorized()
        403 -> AppError.Forbidden()
        404 -> AppError.NotFound()
        429 -> AppError.RateLimited()
        in 500..599 -> AppError.ServerError(code = code)
        else -> AppError.ServerError(
            code = code,
            message = "HTTP $code: $message"
        )
    }
}
