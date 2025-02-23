package com.vango.domain.entities

sealed class AppError(override val message: String) : Exception(message) {
    class DetailedError(message: String) : AppError(message)
    object UnknownError : AppError("Error desconocido")
}