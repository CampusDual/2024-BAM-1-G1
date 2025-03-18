package com.vango.shared.mappers

import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.vango.domain.model.AppError

object FirebaseAuthErrorMapper {
    fun map(exception: Exception): AppError {
        return when (exception) {
            is FirebaseAuthInvalidUserException -> AppError.DetailedError("El usuario no existe.")
            is FirebaseAuthInvalidCredentialsException -> AppError.DetailedError("Correo o contraseña incorrectos.")
            is FirebaseAuthUserCollisionException -> AppError.DetailedError("El correo ya está en uso.")
            is FirebaseAuthWeakPasswordException -> AppError.DetailedError("La contraseña es demasiado débil.")
            is FirebaseAuthEmailException -> AppError.DetailedError("Error con el formato del correo.")
            is FirebaseAuthRecentLoginRequiredException -> AppError.DetailedError("Se requiere un inicio de sesión reciente.")
            is FirebaseAuthInvalidCredentialsException -> AppError.DetailedError("Credenciales inválidas.")
            else -> AppError.UnknownError
        }
    }
}