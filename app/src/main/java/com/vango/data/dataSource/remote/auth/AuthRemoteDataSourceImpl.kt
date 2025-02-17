package com.vango.data.dataSource.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vango.data.dataSource.remote.auth.dto.AuthDtoRequest
import com.vango.data.dataSource.remote.auth.dto.AuthDtoResponse
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

}