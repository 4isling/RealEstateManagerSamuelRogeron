package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.realestatemanagersamuelrogeron.ui.add_screen.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.list_screen.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.map_screen.MapScreen

@Composable
fun Navigation(navController: NavHostController) {
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
                )
            }
            composable(
                route = "${Screen.EstateDetail.route}/{estateId}",
                arguments = listOf(navArgument("estateId") { type = NavType.LongType })
            ) {
                EstateDetailScreen(
                    navController = navController,
                )
            }
            composable(
                route = Screen.AddEstate.route
            )
            {
                AddEstateScreen(
                    navController = navController,
                )
            }
            composable(route = Screen.Map.route){
                MapScreen(
                    navController
                )
            }
        }
    }
}