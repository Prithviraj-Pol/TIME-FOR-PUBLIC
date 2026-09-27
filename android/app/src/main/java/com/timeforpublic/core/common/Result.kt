package com.timeforpublic.core.common

/**
 * Unified result wrapper for all repository and use case operations.
 * Enforces UiState pattern: Loading → Success | Error across every screen.
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: AppError) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun errorOrNull(): AppError? = when (this) {
        is Error -> error
        else -> null
    }

    /**
     * Transform the success value while preserving error/loading states.
     */
    fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
        is Loading -> this
    }
}
