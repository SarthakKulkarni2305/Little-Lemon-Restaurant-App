package com.edwards2kx.littlelemon.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import com.edwards2kx.littlelemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoarding(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little lemon logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(200.dp).padding(vertical = 16.dp)
            )
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.darkGreenLL))
            ) {
                Text(
                    "Let's get to know you",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(top = 32.dp) ) {
                    Text(
                        "Personal Information",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black,
                    )
                }

                OutlinedTextField(
                    value = firstName,
                    label = { Text("First Name") },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { firstName = it })
                OutlinedTextField(
                    value = lastName,
                    label = { Text("Last Name") },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { lastName = it })
                OutlinedTextField(
                    value = email,
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { email = it })

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.yellowLL),
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        if(firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()){
                            sharedPreferences.edit(commit = true) { putString(FIRST_NAME, firstName) }
                            sharedPreferences.edit(commit = true) { putString(LAST_NAME, lastName) }
                            sharedPreferences.edit(commit = true) { putString(EMAIL, email) }
                            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(HomeDestination.route)
                        } else{
                            Toast.makeText(
                                context,
                                "Registration unsuccessful. Please enter all data.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                    Text(text = "Register", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

//@Preview(showBackground = true, device = "id:pixel_4")
//@Composable
//fun OnBoardingPreview() {
//    OnBoarding(navController)
//}