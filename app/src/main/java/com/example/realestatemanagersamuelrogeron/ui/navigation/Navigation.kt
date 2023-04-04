package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.realestatemanagersamuelrogeron.ui.screens.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val estateViewModel = viewModel<EstatesListViewModel>()
    val addEstateViewModel = viewModel<AddEstateViewModel>()
    val estateDetailViewModel= viewModel<EstateDetailViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.EstateList.route,
    ){
        composable(
            route = Screen.EstateList.route
        ){
            EstateListScreen(navController = navController, estatesListViewModel = estateViewModel)
        }

        composable(
            route = Screen.EstateDetail.route ,
            /*arguments = listOf(
                navArgument("id"){
                    type = NavType.LongType
                    defaultValue  = 0
                    nullable = true
                }
            )*/
        ){
            EstateDetailScreen(navController = navController, estateDetailViewModel = estateDetailViewModel)
        }

        composable(route = Screen.AddEstate.route){
            AddEstateScreen(navController = navController, addEstatesViewModel = addEstateViewModel)
        }
    }
}