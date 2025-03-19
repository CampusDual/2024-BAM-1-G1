package com.vango.utils

import com.vango.domain.respositories.AuthRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class AccessTokenInterceptor @Inject constructor(
    private val authRepository: AuthRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        var idToken = authRepository.getCurrentIdToken()
        android.util.Log.d("AccessTokenInterceptor", "Initial idToken: $idToken")

        if (idToken == null) {
            runBlocking {
                idToken = authRepository.refreshIdToken()
                android.util.Log.d("AccessTokenInterceptor", "Refreshed idToken: $idToken")
            }
        }

        val modifiedRequest = if (idToken != null) {
            android.util.Log.d("AccessTokenInterceptor", "Adding Authorization header with token: $idToken")
            originalRequest.newBuilder()
                .header("Authorization", "Bearer $idToken")
                .build()
        } else {
            android.util.Log.w("AccessTokenInterceptor", "No valid idToken available, proceeding without Authorization")
            originalRequest
        }

        return chain.proceed(modifiedRequest)
    }
}