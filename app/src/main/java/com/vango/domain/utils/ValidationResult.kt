package com.vango.domain.utils

data class ValidationResult(
    val hasError: Boolean,
    val errorMessage: String? = null
)

