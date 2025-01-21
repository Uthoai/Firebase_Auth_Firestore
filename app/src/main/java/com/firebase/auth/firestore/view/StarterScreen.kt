package com.firebase.auth.firestore.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.firebase.auth.firestore.Destination

@Composable
fun StarterScreen(navController: NavController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Destination.SignInScreen.toString()) },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destination.SignUpScreen.toString()) },
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "SignUp", fontSize = 20.sp)
        }
    }
}