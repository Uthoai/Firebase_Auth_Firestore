package com.firebase.auth.firestore.model

data class User(
    val id: String = "",
    val name: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null
)
