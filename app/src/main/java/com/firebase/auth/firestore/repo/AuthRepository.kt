package com.firebase.auth.firestore.repo

import androidx.compose.ui.text.resolveDefaults
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository {

    private val tag = "AuthRepository: "
    private val firebaseAuth = FirebaseAuth.getInstance()

    fun isLoggedIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            println(tag + "User is logged in")
            return true
        }
        return false
    }

    suspend fun signUp(email: String, password: String): Boolean {
        try {
            val result = suspendCoroutine { continuation ->
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        println(tag + "signup success")
                        CoroutineScope(Dispatchers.IO).launch {
                            signIn(email, password)
                        }
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        println(tag + "signup failure")
                        continuation.resume(false)
                    }
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "signup exception: ${e.message}")
            return false
        }
    }

    suspend fun signIn(email: String, password: String): Boolean {
        try {
            val result = suspendCoroutine { continuation ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        println(tag + "signin success")
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        println(tag + "signin failure")
                        continuation.resume(false)
                    }
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            println(tag + "signin exception: ${e.message}")
            return false
        }
    }

    fun logout(){
        firebaseAuth.signOut()
        println(tag + "logout success")
    }
}


