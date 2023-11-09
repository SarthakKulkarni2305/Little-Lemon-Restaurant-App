package com.edwards2kx.littlelemon.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.edwards2kx.littlelemon.R
import com.edwards2kx.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    var firstName = sharedPreferences.getString(FIRST_NAME, "Tilly") ?: "Tilly"
    var lastName = sharedPreferences.getString(LAST_NAME, "Doe") ?: "Doe"
    var email = sharedPreferences.getString(EMAIL, "tillydoe@example.com") ?: "tillydoe@example.com"

    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {

        Column(
            // verticalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little lemon logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 16.dp)
            )
            Box(
                //contentAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(top = 32.dp)
                    .weight(0.1f)
            ) {
                Text(
                    "Personal Information",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black,
                )
            }
            Box( contentAlignment = Alignment.BottomCenter ,modifier = Modifier.weight(0.4f)) {
                Column( verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxHeight()) {
                    OutlinedTextField(
                        value = firstName,
                        enabled = false,
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { firstName = it })
                    OutlinedTextField(
                        value = lastName,
                        enabled = false,
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { lastName = it })
                    OutlinedTextField(
                        value = email,
                        enabled = false,
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        onValueChange = { email = it })
                }
            }
            //Box(Modifier.fillMaxSize())
            //Spacer(modifier = Modifier.fillMaxHeight())
            Box( contentAlignment = Alignment.BottomCenter ,modifier = Modifier.weight(0.3f)) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.yellowLL),
                        contentColor = Color.Black,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        sharedPreferences.edit(commit = true) { clear() }
                        navController.navigate(OnboardingDestination.route)
                    }) {
                    Text(text = "Log out", style = MaterialTheme.typography.labelLarge)
                }
            }

//            Column(
//                //verticalArrangement = Arrangement.SpaceBetween,
//                horizontalAlignment = Alignment.Start,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 16.dp)
//            ) {
//
//            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun ProfilePreview() {
    val navController = rememberNavController()
    LittleLemonTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Profile(navController)
        }
    }
}

