package com.vango.data.dataSource.remote.auth

import android.content.Context
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.vango.R
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleSignInClient(private val context: Context
){
    private val tag = "GoogleSignInClient: "
    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    private fun isSingedIn(): Boolean{
        return firebaseAuth.currentUser != null
    }

    suspend fun signIn(): Boolean{
        if(isSingedIn()){
            return true
        }
        try{
            val result = buildCredentialRequest()
            return handleSingIn(result)

        } catch (e: Exception){
            e.printStackTrace()
            if (e is CancellationException) throw e
            return false
        }

    }

    private suspend fun handleSingIn(result: GetCredentialResponse): Boolean{
        val credential = result.credential

        if(credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL)
        {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                return authResult.user != null
            }
            catch (e: GoogleIdTokenParsingException){
                return false
            }

        }
        else{
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        context.getString(R.string.web_client_id)
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(
            request = request, context = context
        )
    }

    suspend fun SignOut(){
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
    }

}