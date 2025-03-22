import com.google.firebase.auth.FirebaseAuth
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.shared.dtos.auth.AuthDtoRequestDto
import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.domain.respositories.AuthRepository
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import com.vango.shared.dtos.auth.AuthWhitTokenRequestDto
import com.vango.shared.dtos.auth.AuthWhitTokenResponseDto
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource:AuthRemoteDataSource,
    private val firebaseAuth: FirebaseAuth

) : AuthRepository {

    private var currentIdToken: String? = null
    private var refreshToken: String? = null
    private var tokenExpirationTime: Long = 0L


//    override suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto> {
//        val credentials = AuthDtoRequestDto(email, password)
//        return authRemoteDataSource.logIn(credentials)
//    }

    override suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto> {
        val credentials = AuthDtoRequestDto(email, password)
        val result = authRemoteDataSource.logIn(credentials)
        if (result.isSuccess) {
            result.getOrNull()?.let { response ->
                currentIdToken = response.idToken
                tokenExpirationTime = System.currentTimeMillis() + 3600 * 1000
            }
        }
        return result
    }

    override suspend fun logInWhitToken(token: String): Response<AuthWhitTokenResponseDto> {
        val credentials = AuthWhitTokenRequestDto(token)
        val response = authRemoteDataSource.logInWhitToken(credentials)
        if (response.isSuccessful) {
            response.body()?.let { body ->
                currentIdToken = body.idToken
                tokenExpirationTime = System.currentTimeMillis() + 3600 * 1000
            }
        }
        return response
    }

    override  suspend fun recoverPassword (email: String): Result<Boolean>{
        return authRemoteDataSource.recoverPassword(email)
    }

    override suspend fun signUp(userRequestDto: AuthSignUpUserRequestDto): Response<AuthSignUpUserResponseDto>
    {
        return authRemoteDataSource.signUp(userRequestDto)
    }

    override fun logout() {
        authRemoteDataSource.logout()
        currentIdToken = null
    }

    override suspend fun verifyUserEmail(verifyUserEmailRequestDto: AuthVerifyUserEmailUpUserRequestDto): Response<AuthVerifyUserEmailUpUserResponseDto>
    {
        return authRemoteDataSource.verifyUserEmail(verifyUserEmailRequestDto)
    }

    override fun getCurrentIdToken(): String? {
        if (currentIdToken != null && System.currentTimeMillis() > tokenExpirationTime) {
            return null
        }
        return currentIdToken
    }

    override suspend fun refreshIdToken(): String? {
        val user = firebaseAuth.currentUser
        return if (user != null) {
            val tokenResult = user.getIdToken(true).result
            currentIdToken = tokenResult.token
            tokenExpirationTime = System.currentTimeMillis() + 3600 * 1000
            android.util.Log.d("AuthRepository", "Token refreshed successfully: $currentIdToken")
            currentIdToken
        } else {
            android.util.Log.w("AuthRepository", "No current user, cannot refresh token")
            null
        }
    }
}
