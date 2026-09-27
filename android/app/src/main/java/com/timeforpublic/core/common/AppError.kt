package com.timeforpublic.core.common

/**
 * Structured error model for all error states across the application.
 * Maps HTTP status codes and network failures to user-friendly messages.
 */
sealed class AppError(
    open val message: String,
    open val cause: Throwable? = null
) {
    /** No internet connectivity detected. */
    data class NetworkError(
        override val message: String = "No internet connection. Please check your network.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** Server returned an error response. */
    data class ServerError(
        val code: Int,
        override val message: String = "Server error occurred. Please try again later.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** HTTP 401 — token expired or invalid. */
    data class Unauthorized(
        override val message: String = "Session expired. Please log in again.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** HTTP 403 — insufficient permissions. */
    data class Forbidden(
        override val message: String = "You don't have permission to perform this action.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** HTTP 404 — resource not found. */
    data class NotFound(
        override val message: String = "The requested resource was not found.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** HTTP 429 — rate limited. */
    data class RateLimited(
        override val message: String = "Too many requests. Please wait and try again.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** Request timed out. */
    data class Timeout(
        override val message: String = "Request timed out. Please try again.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)

    /** Catch-all for unexpected errors. */
    data class Unknown(
        override val message: String = "An unexpected error occurred.",
        override val cause: Throwable? = null
    ) : AppError(message, cause)
}
