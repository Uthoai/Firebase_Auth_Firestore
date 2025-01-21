package com.firebase.auth.firestore.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.firebase.auth.firestore.Destination
import com.firebase.auth.firestore.repo.AuthRepository
import com.firebase.auth.firestore.ui.theme.Firebase_Auth_FirestoreTheme
import com.firebase.auth.firestore.view.home.HomeScreen
import com.firebase.auth.firestore.view.login.SignInScreen
import com.firebase.auth.firestore.view.signup.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authRepository = AuthRepository()

        setContent {
            Firebase_Auth_FirestoreTheme {
                val isLoggedIn by rememberSaveable { mutableStateOf(authRepository.isLoggedIn()) }

                if (isLoggedIn) {
                    NavigationScreen(Destination.HomeScreen.toString())
                } else {
                    NavigationScreen(Destination.StarterScreen.toString())
                }
            }
        }
    }
}

@Composable
private fun NavigationScreen(destination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = destination) {
        composable(route = Destination.StarterScreen.toString()) {
            StarterScreen(navController)
        }
        composable(route = Destination.SignInScreen.toString()) {
            SignInScreen(navController)
        }
        composable(route = Destination.SignUpScreen.toString()) {
            SignUpScreen(navController)
        }
        composable(route = Destination.HomeScreen.toString()) {
            HomeScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Firebase_Auth_FirestoreTheme {
    }
}