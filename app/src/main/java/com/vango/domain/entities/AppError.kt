package com.vango.domain.entities

sealed class AppError: Exception() {
    data object UnknownError: AppError()
    data class DetailedError(val body: String): AppError()
    data object NetworkError: AppError()
    data object TimeoutError: AppError()
    data object EmptyBodyError: AppError()
}