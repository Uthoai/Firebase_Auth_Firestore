package com.firebase.auth.firestore.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavController
import com.firebase.auth.firestore.Destination
import com.firebase.auth.firestore.repo.AuthRepository


@Composable
fun HomeScreen(navController: NavController) {
    val authRepository = AuthRepository()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Home Page",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = Color.Black
            )
        )

        Button(
            onClick = {
                authRepository.logout()
                navController.navigate(Destination.StarterScreen.toString())
            }) {
            Text("Logout")
        }
    }

}


