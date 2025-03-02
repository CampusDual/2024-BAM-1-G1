package com.vango.utils.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AccessTokenInterceptor @Inject constructor(
    private val firebase: FirebaseAuth
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = runBlocking { getAccessToken() }

        val request = if (accessToken != null) {
            newRequestWithAccessToken(chain.request(), accessToken)
        } else {
            chain.request().newBuilder().build()
        }

        return chain.proceed(request)
    }


    private suspend fun getAccessToken(): String? = suspendCoroutine { continuation ->
        firebase.currentUser?.getIdToken(true)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result?.token)
            } else {
                task.exception?.printStackTrace()
                continuation.resume(null)
            }
        }
    }

    private fun newRequestWithAccessToken(request: Request, accessToken: String): Request {
        return request.newBuilder()
            .apply {
                if (accessToken.isNotEmpty())
                    header("Authorization", "Bearer $accessToken")
            }
            .build()
    }
}