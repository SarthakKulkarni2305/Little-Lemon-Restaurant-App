package com.edwards2kx.littlelemon.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val email = sharedPreferences.getString(EMAIL, "") ?: ""
    val hasUserData = email.isNotBlank()

    NavHost(
        navController = navController,
        startDestination = if (hasUserData) HomeDestination.route else OnboardingDestination.route
    ) {
        composable(OnboardingDestination.route) { OnBoarding(navController = navController) }
        composable(HomeDestination.route) { Home(navController = navController) }
        composable(ProfileDestination.route) { Profile(navController = navController) }
    }
}