package com.vango.data.dataSource.remote.auth


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
import com.vango.data.dataSource.remote.auth.dto.UserDto
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class AuthRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):AuthRemoteDataSource{

    override suspend fun login(authDto: AuthDtoRequest): AuthDtoResponse {
        return suspendCoroutine { result ->
            auth.signInWithEmailAndPassword(authDto.email, authDto.password)
                .addOnSuccessListener { authResult ->
                    result.resume(AuthDtoResponse(authResult?.user?.uid))
                }
                .addOnFailureListener { exception ->
                    result.resumeWithException(exception)
                }

        }
    }

    override suspend fun recoverPassword(email: String): Boolean {
        return suspendCoroutine { result ->
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener { authResult ->
                    result.resume(true)
                }
                .addOnFailureListener { exception ->
                    result.resumeWithException(exception)
                }
        }
    }

//    override suspend fun changePass(newPassword : String): Boolean {
//        return suspendCoroutine { result ->
//            auth.confirmPasswordReset(newPassword,)
//                .addOnSuccessListener { authResult ->
//                    result.resume(true)
//                }
//                .addOnFailureListener { exception ->
//                    result.resumeWithException(exception)
//                }
//        }
//    }

    override suspend fun signUp(dto: UserDto): Pair<Boolean, String> {
        val uuid = createAuthUser(dto.email, dto.password)
        return if (uuid.isNotEmpty()) {
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
                if (task.isSuccessful) {
                    result.resume(task.result.user?.uid ?: "")
                }
            }.addOnFailureListener {
                result.resume("")
            }
        }
    }

    private suspend fun createDataBaseUser(uuid: String, dto: UserDto): Boolean {
        firestore.collection("users").document(uuid)
        return suspendCoroutine { result ->
            firestore.collection("users").document().set(dto)
                .addOnSuccessListener {
                    result.resume(true)
                }.addOnFailureListener {
                    result.resume(false)
                }
        }
    }
}