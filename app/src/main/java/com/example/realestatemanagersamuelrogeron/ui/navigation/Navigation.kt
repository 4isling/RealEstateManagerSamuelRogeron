package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.realestatemanagersamuelrogeron.ui.screens.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

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

            composable(route = Screen.AddEstate.route)
            {
                AddEstateScreen(
                    navController = navController,
                )
            }
        }
    }
}