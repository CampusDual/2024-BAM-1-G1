import com.google.firebase.auth.FirebaseAuthException
import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
import com.vango.data.dataSource.remote.auth.dto.UserDto
import com.vango.domain.entities.AppError
import com.vango.domain.respository.AuthRepository
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

    override suspend fun signUp(email: String, password: String): Boolean {
        val dto = UserDto("",email, password)
        val responseDto: Pair<Boolean, String> = authRemoteDataSource.signUp(dto)
        return responseDto.first

    }
}
