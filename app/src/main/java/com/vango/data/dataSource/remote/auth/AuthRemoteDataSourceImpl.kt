package com.vango.data.dataSource.remote.auth


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vango.data.dataSource.remote.api.UserApi
import com.vango.shared.dtos.AuthDtoRequest
import com.vango.shared.dtos.AuthDtoResponse
import com.vango.shared.dtos.UserDto
import com.vango.domain.entities.AppError
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import com.vango.shared.mappers.FirebaseAuthErrorMapper
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AuthRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userApi: UserApi,
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

    override suspend fun signUp(createUserRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
    {
        return userApi.createUser(createUserRequestDto)
    }

    override fun logout() {

    }

    override suspend fun getUser(): List<String> {
        return listOf()
    }





}