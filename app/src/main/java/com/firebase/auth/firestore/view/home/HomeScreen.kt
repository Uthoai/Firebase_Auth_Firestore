package com.firebase.auth.firestore.view.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.firebase.auth.firestore.Destination
import com.firebase.auth.firestore.model.User
import com.firebase.auth.firestore.repo.AuthRepository
import com.firebase.auth.firestore.service.FireStoreClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavController) {
    val localContext = LocalContext.current
    val fireStoreClient = FireStoreClient()

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val phoneNumber = remember { mutableStateOf("") }

    var user = User(
        name = name.value,
        email = email.value,
        phoneNumber = phoneNumber.value
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Add User",
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = Color.Black
            )
        )

        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            singleLine = true,
            label = { Text(text = "full name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            singleLine = true,
            label = { Text(text = "email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber.value,
            onValueChange = { phoneNumber.value = it },
            singleLine = true,
            label = { Text(text = "phone") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    fireStoreClient.insertUser(
                        User(
                            name = name.value,
                            email = email.value,
                            phoneNumber = phoneNumber.value
                        )
                    ).collect { id->
                        user = user.copy(id = id.toString() ?: "")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = "Add User", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    fireStoreClient.updateUser(
                        User(
                            name = name.value,
                            email = email.value,
                            phoneNumber = phoneNumber.value
                        )
                    ).collect{ result->
                        if (result){
                            Toast.makeText(localContext, "User Data Update Successfully.", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(localContext, "User Data Update Failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = "Update User", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    fireStoreClient.getUserByEmail(
                        email = email.value
                    ).collect { result->
                        if (result != null){
                            /*name.value = result.name.toString()
                            email.value = result.email.toString()
                            phoneNumber.value = result.phoneNumber.toString()*/
                            Toast.makeText(localContext, result.phoneNumber.toString(), Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(localContext, "User not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = "Get User", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

    }
}





