package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.realestatemanagersamuelrogeron.ui.add_screen.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemLeftBar
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.EstateEditScreen
import com.example.realestatemanagersamuelrogeron.ui.list_screen.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.map_screen.MapScreen
import com.example.realestatemanagersamuelrogeron.ui.settings_screen.SettingScreen

@Composable
fun TabletLayout(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    Row(modifier = Modifier
        .background(MaterialTheme.colorScheme.primaryContainer),
        ) {
        RemLeftBar(
            onClickSetting = { navController.navigate(Screen.Settings.route) },
            onClickMap = { navController.navigate(Screen.Map.route) },
            onClickAdd = { navController.navigate(Screen.AddEstate.route) }
        )

        NavHost(
            navController = navController,
            startDestination = Screen.Map.route // Default start screen
        ) {
            composable(Screen.Map.route) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.weight(1f)) {
                        EstateListScreen(
                            navController = navController,
                            windowSizeClass = windowSizeClass,
                            onEstateSelected = { estateId ->
                                navController.navigate("${Screen.EstateDetail.route}/$estateId")
                            }
                        )
                    }
                    Column(modifier = Modifier.weight(2f)) {
                        MapScreen(
                            navController = navController,
                            windowSizeClass = windowSizeClass,
                            onEstateSelected = { estateId ->
                                navController.navigate("${Screen.EstateDetail.route}/$estateId")
                            }
                        )
                    }
                }
            }

            composable(
                route = "${Screen.EstateDetail.route}/{estateId}",
                arguments = listOf(navArgument("estateId") {
                    type = NavType.LongType
                })
            ) { backStackEntry ->
                val estateId = backStackEntry.arguments?.getLong("estateId")
                estateId?.let {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.weight(1f)) {
                            EstateListScreen(
                                navController = navController,
                                windowSizeClass = windowSizeClass,
                                onEstateSelected = { selectedEstateId ->
                                    navController.navigate("${Screen.EstateDetail.route}/$selectedEstateId")
                                }
                            )
                        }
                        Column(modifier = Modifier.weight(2f)) {
                            EstateDetailScreen(navController = navController, windowSizeClass = windowSizeClass)
                        }

                    }
                }
            }

            composable(
                route = "${Screen.Edit.route}/{estateId}",
                arguments = listOf(navArgument("estateId") {
                    type = NavType.LongType
                })
            ){  backStackEntry ->
                val estateId = backStackEntry.arguments?.getLong("estateId")
                estateId?.let {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.weight(1f)) {
                            EstateListScreen(
                                navController = navController,
                                windowSizeClass = windowSizeClass,
                                onEstateSelected = { selectedEstateId ->
                                    navController.navigate("${Screen.EstateDetail.route}/$selectedEstateId")
                                }
                            )
                        }
                        Column(modifier = Modifier.weight(2f)) {
                            EstateEditScreen(navController = navController, windowSizeClass = windowSizeClass)
                        }

                    }
                }
            }


            composable(Screen.AddEstate.route) {
                AddEstateScreen(navController = navController, windowSizeClass = windowSizeClass)
            }

            composable(Screen.Settings.route) {
                SettingScreen(navController = navController, windowSizeClass = windowSizeClass)
            }
        }
    }
}