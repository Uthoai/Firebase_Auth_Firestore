package com.firebase.auth.firestore.service

import android.util.Log
import com.firebase.auth.firestore.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class FireStoreClient {
    private val db = FirebaseFirestore.getInstance()
    private val collectionRef = "users"

    fun insertUser(user: User): Flow<String?> {
        return callbackFlow {
            db.collection(collectionRef)
                .add(user.toHashMap())
                .addOnSuccessListener { documentReference ->
                    Log.d("tag", "DocumentSnapshot added with ID: ${documentReference.id}")

                    CoroutineScope(Dispatchers.IO).launch {
                        updateUser(user.copy(id = documentReference.id)).collect{
                            Log.d("tag", "User Data Update Successfully.")
                        }
                    }

                    trySend(documentReference.id)
                }
                .addOnFailureListener { e ->
                    Log.w("tag", "Error adding document", e)
                    trySend(null)
                }
            awaitClose()
        }
    }

    fun updateUser(user: User): Flow<Boolean> {
        return callbackFlow {
            db.collection(collectionRef)
                .document(user.id)
                .set(user.toHashMap())
                .addOnSuccessListener {
                    Log.d("tag", "User Data Update Successfully.")
                    trySend(true)
                }
                .addOnFailureListener { e ->
                    Log.w("tag", "Error adding document", e)
                    trySend(false)
                }
            awaitClose()
        }
    }

    /*fun getUser(email: String): Flow<User?> {
        return callbackFlow {
            db.collection(collectionRef)
                .document(email)
                .get()
                .addOnSuccessListener { result->
                    var user: User? = null

                    if (result.exists()) {
                        // Convert the document's data to a User object
                        user = result.data?.toUser()
                        Log.d("tag", "User ID: ${result.id}")
                        trySend(user).isSuccess
                    } else {
                        Log.d("TAG", "getUser: null")
                        trySend(null).isSuccess
                    }

                    *//*if (result.exists()){
                        for (document in result) {
                            if (document.data["email"] == email) {
                                user = document.data.toUser()
                                Log.d("tag", "User ID: ${document.id}")
                                trySend(user)
                            }
                        }
                    }

                    if (user == null) {
                        Log.d("TAG", "getUser: null")
                        trySend(null)
                    }*//*

                }
                .addOnFailureListener { e ->
                    Log.w("tag", "Error adding document", e)
                    trySend(null)
                }
            awaitClose()
        }
    }*/

    fun getUserByEmail(email: String): Flow<User?> {
        return callbackFlow {
            val db = FirebaseFirestore.getInstance()
            db.collection("users")
                .whereEqualTo("email", email) // Query Firestore by email
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        val document = querySnapshot.documents[0] // Get the first matching document
                        val user = document.toObject(User::class.java) // Convert to User object
                        trySend(user).isSuccess
                    } else {
                        Log.d("TAG", "No user found with email: $email")
                        trySend(null).isSuccess
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error fetching document", e)
                    trySend(null).isSuccess
                }
            awaitClose { }
        }
    }


    private fun User.toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "id" to id,
            "name" to name!!,
            "email" to email!!,
            "phoneNumber" to phoneNumber!!
        )
    }

    private fun Map<String, Any>.toUser(): User {
        return User(
            id = this["id"] as String,
            name = this["name"] as String,
            email = this["email"] as String,
            phoneNumber = this["phoneNumber"] as String
        )
    }



}
