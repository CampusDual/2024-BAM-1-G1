import com.vango.domain.respository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    override suspend fun login(email: String, password: String): Boolean {
        // Lógica de autenticación
        return email == "test@example.com" && password == "password123"
    }
}
