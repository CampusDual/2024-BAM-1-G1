import com.vango.data.dataSource.remote.auth.AuthRemoteDataSource
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.UserDto
import com.vango.domain.respository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(private val authRemoteDataSource:AuthRemoteDataSource) : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {

        val credentials = AuthDtoRequest(email,password)
        val dto = authRemoteDataSource.login(credentials)
        if(!dto.uuid.isNullOrEmpty()){
            return true
        }

        return false
    }

    override suspend fun signUp(email: String, password: String): Boolean {
        val dto = UserDto("",email, password)
        val responseDto: Pair<Boolean, String> = authRemoteDataSource.signUp(dto)
        return responseDto.first

    }
}
