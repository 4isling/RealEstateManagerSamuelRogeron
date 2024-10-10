package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(windowSizeClass: WindowSizeClass) {
    val isTablet = isTablet()
    when {
        isTablet && windowSizeClass.widthSizeClass >= WindowWidthSizeClass.Medium -> {
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

@Composable
fun isTablet(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.smallestScreenWidthDp >= 600
}