package com.firebase.auth.firestore.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.firebase.auth.firestore.service.GoogleSignInClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StarterScreen(navController: NavController){
    val localContext = LocalContext.current

    val googleSignInClient = GoogleSignInClient(localContext)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(Destination.SignInScreen.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "Login", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destination.SignUpScreen.toString()) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "SignUp", fontSize = 20.sp)
        }

        Spacer(Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.google_icon), // Replace with your drawable
            contentDescription = "Sample Image",
            modifier = Modifier
                .size(48.dp)
                .clickable {
                    CoroutineScope(Dispatchers.Main).launch {
                        if (googleSignInClient.signIn()) {
                            navController.navigate(Destination.HomeScreen.toString())
                        } else {
                            Toast.makeText(
                                localContext,
                                "use another email id",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }, // Set size if needed
            contentScale = ContentScale.Fit, // Optional: Scale the image
        )
    }
}