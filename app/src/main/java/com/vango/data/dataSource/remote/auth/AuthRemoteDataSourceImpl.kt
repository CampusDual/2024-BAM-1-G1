package com.vango.data.dataSource.remote.auth


import com.google.firebase.auth.FirebaseAuth
import com.vango.data.dataSource.remote.api.UserApi
import com.vango.shared.dtos.auth.AuthDtoRequestDto
import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.domain.model.AppError
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import com.vango.shared.dtos.auth.AuthWhitTokenRequestDto
import com.vango.shared.dtos.auth.AuthWhitTokenResponseDto
import com.vango.shared.mappers.FirebaseAuthErrorMapper
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val userApi: UserApi,
) : AuthRemoteDataSource {

    override suspend fun logIn(userLoginDto: AuthDtoRequestDto): Result<AuthDtoResponseDto> {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(userLoginDto.email, userLoginDto.password)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    if (user != null) {
                        user.getIdToken(false).addOnSuccessListener { tokenResult ->
                            val idToken = tokenResult.token
                            continuation.resume(Result.success(AuthDtoResponseDto(user.uid, idToken)))
                        }.addOnFailureListener { exception ->
                            continuation.resume(Result.failure(FirebaseAuthErrorMapper.map(exception)))
                        }
                    } else {
                        continuation.resume(Result.failure(AppError.DetailedError("El usuario es nulo.")))
                    }
                }
                .addOnFailureListener { exception ->
                    continuation.resume(Result.failure(FirebaseAuthErrorMapper.map(exception)))
                }
        }
    }

    override suspend fun logInWhitToken(userLoginWhitTokenDto: AuthWhitTokenRequestDto): Response<AuthWhitTokenResponseDto> {
        return suspendCoroutine { continuation ->
            auth.signInWithCustomToken(userLoginWhitTokenDto.token)
                .addOnSuccessListener { authResult ->
                    val user = authResult.user
                    if (user != null) {
                        user.getIdToken(false).addOnSuccessListener { tokenResult ->
                            val idToken = tokenResult.token
                            continuation.resume(Response.success(AuthWhitTokenResponseDto(user.uid, idToken)))
                        }.addOnFailureListener { exception ->
                            val errorBody = FirebaseAuthErrorMapper.map(exception).message?.toResponseBody(null)
                            continuation.resume(Response.error(401, errorBody ?: "Error al obtener token".toResponseBody(null)))
                        }
                    } else {
                        val errorBody = "El usuario es nulo.".toResponseBody(null)
                        continuation.resume(Response.error(400, errorBody))
                    }
                }
                .addOnFailureListener { exception ->
                    val errorMessage = FirebaseAuthErrorMapper.map(exception).message ?: "Error desconocido"
                    val errorBody = errorMessage.toResponseBody(null)
                    continuation.resume(Response.error(401, errorBody))
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

    override suspend fun signUp(authSignUpUserRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto> {
        return userApi.signUp(authSignUpUserRequestDto)
    }

    override suspend fun verifyUserEmail(verifyUserEmailRequestDto: AuthVerifyUserEmailUpUserRequestDto): Response<AuthVerifyUserEmailUpUserResponseDto> {
        return userApi.verifyUserEmail(verifyUserEmailRequestDto)
    }

    override fun logout() {
        auth.signOut()
    }

    override suspend fun getUser(): List<String> {
        return listOf()
    }


}