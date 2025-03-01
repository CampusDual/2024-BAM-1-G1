import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.shared.dtos.AuthDtoRequest
import com.vango.shared.dtos.AuthDtoResponse
import com.vango.shared.dtos.UserDto
import com.vango.domain.respositories.AuthRepository
import com.vango.shared.dtos.user.CreateUserRequestDto
import com.vango.shared.dtos.user.CreateUserResponseDto
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource:AuthRemoteDataSource) : AuthRepository {

    override suspend fun logIn(email: String, password: String): Result<AuthDtoResponse> {
        val credentials = AuthDtoRequest(email, password)
        return authRemoteDataSource.logIn(credentials)
    }

    override  suspend fun recoverPassword (email: String): Result<Boolean>{
        return authRemoteDataSource.recoverPassword(email)
    }

    override suspend fun signUp(userRequestDto: CreateUserRequestDto): Response<CreateUserResponseDto>
    {
        return authRemoteDataSource.signUp(userRequestDto)
    }
}
