package com.example.realestatemanagersamuelrogeron.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.realestatemanagersamuelrogeron.ui.screens.AddEstateScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.screens.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
) {
    val estateViewModel = viewModel<EstatesListViewModel>()
    val addEstateViewModel = viewModel<AddEstateViewModel>()
    val estateDetailViewModel= viewModel<EstateDetailViewModel>()
    NavHost(
        navController = navController,
        startDestination = Screen.EstateList.route,
        route = ROOT_GRAPH_ROUTE
    ){

        composable(
            route = Screen.EstateList.route
        ){
            EstateListScreen(navController = navController, estatesListViewModel = estateViewModel)
        }

        composable(route = Screen.EstateDetail.route){
            EstateDetailScreen(navController = navController, estateDetailViewModel = estateDetailViewModel)
        }

        composable(route = Screen.AddEstate.route){
            AddEstateScreen(navController = navController, addEstatesViewModel = addEstateViewModel)
        }
    }
}