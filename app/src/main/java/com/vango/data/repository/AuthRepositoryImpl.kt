import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.shared.dtos.auth.AuthDtoRequestDto
import com.vango.shared.dtos.auth.AuthDtoResponseDto
import com.vango.domain.respositories.AuthRepository
import com.vango.shared.dtos.auth.AuthSignUpUserRequestDto
import com.vango.shared.dtos.auth.AuthSignUpUserResponseDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserRequestDto
import com.vango.shared.dtos.auth.AuthVerifyUserEmailUpUserResponseDto
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource:AuthRemoteDataSource) : AuthRepository {

    override suspend fun logIn(email: String, password: String): Result<AuthDtoResponseDto> {
        val credentials = AuthDtoRequestDto(email, password)
        return authRemoteDataSource.logIn(credentials)
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
    }

    override suspend fun verifyUserEmail(verifyUserEmailRequestDto: AuthVerifyUserEmailUpUserRequestDto): Response<AuthVerifyUserEmailUpUserResponseDto>
    {
        return authRemoteDataSource.verifyUserEmail(verifyUserEmailRequestDto)
    }
}
