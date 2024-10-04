package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation( windowSizeClass: WindowSizeClass) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            val navController = rememberNavController()
            TabletLayout(
                navController = navController,
                windowSizeClass = windowSizeClass
            )
        }

        else -> {
            val navController = rememberNavController()
            PhoneNavigation(
                navController = navController,
                windowSizeClass = windowSizeClass
            )
        }
    }
}