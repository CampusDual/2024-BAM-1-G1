package com.vango.data.dataSource.remote.auth


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
import com.vango.data.dataSource.remote.auth.dto.UserDto
import com.vango.domain.entities.AppError
import com.vango.utils.mappers.FirebaseAuthErrorMapper
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):AuthRemoteDataSource{

    override suspend fun logIn(userLoginDto: AuthDtoRequest): Result<AuthDtoResponse> {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(userLoginDto.email, userLoginDto.password)
                .addOnSuccessListener { authResult ->
                    val userId = authResult.user?.uid
                    if (userId != null) {
                        continuation.resume(Result.success(AuthDtoResponse(userId)))
                    } else {
                        continuation.resume(Result.failure(AppError.DetailedError("El usuario es nulo.")))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(FirebaseAuthErrorMapper.map(exception)))
                }
        }
    }


    override suspend fun recoverPassword(email: String): Result<Boolean> {
        return suspendCoroutine { continuation ->
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    continuation.resumeWith(runCatching { Result.success(true) })
                }
                .addOnFailureListener { exception ->
                    val mappedError = FirebaseAuthErrorMapper.map(exception)
                    continuation.resumeWith(runCatching { Result.failure(mappedError) })
                }
        }
    }




    override suspend fun signUp(dto: UserDto): Pair<Boolean, String> {
        val uuid = createAuthUser(dto.email, dto.password)

        return if (uuid.isNotBlank()) {
            dto.uuid = uuid

            Pair(createDataBaseUser(uuid, dto), uuid)
        } else {
            Pair(false, "Error al crear el usuario")
        }
    }

    override fun logout() {

    }

    override suspend fun getUser(): List<String> {
        return listOf()
    }


    private suspend fun createAuthUser(mail: String, password: String): String {

        return suspendCoroutine { result ->
            auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener { task ->
                Log.d("createAuthUser", "createAuthUser: ${task.isSuccessful}")
                if (task.isSuccessful) {
                    result.resume(task.result.user?.uid ?: "")
                }
            }.addOnFailureListener {
                result.resume("")
            }
        }
    }

    private suspend fun createDataBaseUser(uuid: String, dto: UserDto): Boolean {
        return suspendCoroutine { result ->
            firestore.collection("users").document(uuid).set(dto)
                .addOnSuccessListener {
                    result.resume(true)
                }.addOnFailureListener {
                    result.resume(false)
                }
        }
    }
}