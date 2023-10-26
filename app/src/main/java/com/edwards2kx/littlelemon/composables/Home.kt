package com.edwards2kx.littlelemon.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.edwards2kx.littlelemon.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavHostController) {
    val urlServer = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json";
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Home Page") },
                actions = {
                    Image(
                        painterResource(id = R.drawable.profile),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(64.dp)
                            .clip(CircleShape)
                            .clickable {
                                navController.navigate(ProfileDestination.route)
                            }
                    )
                }
            )
        },

        ) {

    }
    //Text("Home Screen")
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    val controller = rememberNavController()
    Home(controller)
}