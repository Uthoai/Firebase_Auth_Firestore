package com.firebase.auth.firestore.view.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.firebase.auth.firestore.Destination
import com.firebase.auth.firestore.R
import com.firebase.auth.firestore.repo.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navController: NavController){
    val localContext = LocalContext.current
    val authRepository = AuthRepository()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SignIn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(40.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            singleLine = true,
            label = { Text(text = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            singleLine = true,
            label = { Text(text = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Button(
            onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                    if (authRepository.signIn(email.value,password.value)){
                        navController.navigate(Destination.HomeScreen.toString())
                    }else{
                        Toast.makeText(localContext, "check email/password", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}