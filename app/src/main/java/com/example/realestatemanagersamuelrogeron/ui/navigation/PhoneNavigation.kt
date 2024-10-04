package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.realestatemanagersamuelrogeron.ui.add_screen.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.EstateEditScreen
import com.example.realestatemanagersamuelrogeron.ui.list_screen.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.map_screen.MapScreen
import com.example.realestatemanagersamuelrogeron.ui.settings_screen.SettingScreen

@Composable
fun PhoneNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {

    NavHost(
        navController = navController,
        startDestination = "estate",
    ) {
        navigation(
            startDestination = Screen.EstateList.route,
            route = "estate"
        ) {
            composable(
                route = Screen.EstateList.route
            ) {
                EstateListScreen(
                    navController = navController,
                    windowSizeClass = windowSizeClass
                )
            }

            composable(
                route = "${Screen.EstateDetail.route}/{estateId}",
                arguments = listOf(navArgument("estateId") { type = NavType.LongType })
            ) {
                EstateDetailScreen(
                    navController = navController,
                    windowSizeClass = windowSizeClass
                )
            }

            composable(route = Screen.Map.route) {
                MapScreen(
                    navController = navController,
                    windowSizeClass = windowSizeClass
                )
            }

            composable(route = Screen.AddEstate.route)
            {
                AddEstateScreen(
                    navController = navController,
                    windowSizeClass = windowSizeClass
                )
            }

            composable(route = Screen.Settings.route){
                SettingScreen(navController = navController, windowSizeClass = windowSizeClass)
            }

            composable(
                route = "${Screen.Edit.route}/{estateId}",
                arguments = listOf(navArgument("estateId") { type = NavType.LongType })
            ){
                EstateEditScreen(
                    navController = navController,
                    windowSizeClass = windowSizeClass,
                )
            }
        }
    }
}