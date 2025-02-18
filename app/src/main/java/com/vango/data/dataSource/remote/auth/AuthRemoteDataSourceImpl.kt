package com.vango.data.dataSource.remote.auth


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
import com.vango.data.dataSource.remote.auth.dto.UserDto
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class AuthRemoteDataSourceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
):AuthRemoteDataSource{

    override suspend fun login (authDto: AuthDtoRequest): AuthDtoResponse{
        return suspendCoroutine { result  ->
            auth.signInWithEmailAndPassword(authDto.email,authDto.password)
                .addOnCompleteListener{ authResult ->
                    result.resume(AuthDtoResponse(authResult.result.user?.uid))

                }
        }
    }

    override suspend fun signUp(dto: UserDto): Pair<Boolean, String> {
        val uuid = CreateAuthUser(dto.email, dto.password)
        return if (uuid.isNotEmpty()) {
            Pair(CreateDataBaseUser(uuid, dto), uuid)
        } else {
            Pair(false, "Error al crear el usuario")
        }
    }

    override fun logout() {
    }

    override suspend fun getUser(): List<String> {
        return listOf()
    }


    private suspend fun CreateAuthUser(mail: String, password: String): String {

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

    private suspend fun CreateDataBaseUser(uuid: String, dto: UserDto): Boolean {
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