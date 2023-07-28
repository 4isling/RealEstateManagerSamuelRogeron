package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

@Composable
fun DrawerListContent(
    viewModel: EstatesListViewModel,
    navController: NavController,
    drawerState: DrawerState
) {
    DismissibleNavigationDrawer(
        drawerState= drawerState,
        drawerContent = {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row (Modifier.clickable{
                navController.navigate(Screen.Map.route)
            }){
                Icon(imageVector = Icons.Default.Map, contentDescription = "Map icon")
                Text(text = "Map")
            }

            Row {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "settings Icon")
                Text(text = "Setting")
            }
        }
    }) {

    }


}