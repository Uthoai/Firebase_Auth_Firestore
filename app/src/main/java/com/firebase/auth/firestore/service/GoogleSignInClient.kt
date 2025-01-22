package com.firebase.auth.firestore.service

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class GoogleSignInClient(
    private val context: Context,
) {
    private val tag = "GoogleSignInClient: "

    private val credentialManager = CredentialManager.create(context)
    private val firebaseAuth = FirebaseAuth.getInstance()

    private fun isSignIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            Log.d("TAG", "User is logged in")
            return true
        }
        return false
    }

    suspend fun signIn(): Boolean {
        if (isSignIn()) {
            return true
        }

        try {
            val result = buildCredentialRequest()
            return handleSignInResult(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "signin exception: ${e.message}")
        }
        return false
    }

    private suspend fun handleSignInResult(result: GetCredentialResponse): Boolean {
        val credential = result.credential

        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                Log.d("TAG", "Name: ${tokenCredential.displayName}")
                Log.d("TAG", "Email: ${tokenCredential.id}")
                Log.d("TAG", "Picture: ${tokenCredential.profilePictureUri}")
                Log.d("TAG", "phoneNumber: ${tokenCredential.phoneNumber}")

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()

                if (authResult.user != null) {
                    Log.d("TAG", "User is logged in successfully")
                    return true
                }

            } catch (e: Exception) {
                e.printStackTrace()
                if (e is GoogleIdTokenParsingException) throw e
                println(tag + "credential parse exception: ${e.message}")
            }
        } else{
            println(tag + "credential is null")
            return false
        }
        return true
    }

    suspend fun signOut(){
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        firebaseAuth.signOut()
        Log.d("TAG", "signOut: Success")
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        "298736056976-cc97or21q1k0d05v6nib8n8pr1cu4o03.apps.googleusercontent.com"
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            ).build()

        return credentialManager.getCredential(
            request = request,
            context = context
        )
    }
}