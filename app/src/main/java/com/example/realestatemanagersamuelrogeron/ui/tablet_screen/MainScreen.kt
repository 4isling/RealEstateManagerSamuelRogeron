package com.example.realestatemanagersamuelrogeron.ui.tablet_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemLeftBar
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.EstateDetailScreen
import com.example.realestatemanagersamuelrogeron.ui.list_screen.EstateListScreen
import com.example.realestatemanagersamuelrogeron.ui.map_screen.MapScreen
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@Composable
fun MainScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            // Phone layout
            Column {
                EstateListScreen(navController = navController, windowSizeClass = windowSizeClass)
                EstateDetailScreen(navController = navController, windowSizeClass = windowSizeClass)
                MapScreen(navController = navController, windowSizeClass = windowSizeClass)
            }
        }
        WindowWidthSizeClass.Medium -> {
            // Small tablet layout
            Column {
                EstateListScreen(navController = navController, windowSizeClass = windowSizeClass)
                EstateDetailScreen(navController = navController, windowSizeClass = windowSizeClass)
            }
        }
        WindowWidthSizeClass.Expanded -> {
            // Large tablet layout
            var isMap by remember {
                mutableStateOf(true)
            }
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route
            Row {
                // Left-side bar
                Column(modifier = Modifier.width(60.dp)) {
                    RemLeftBar(
                        onClickSetting = {
                            navController.navigate(Screen.Settings.route)
                        },
                        onClickMap = {
                            isMap = true
                        },
                        onClickAdd = {
                            navController.navigate(Screen.AddEstate.route)
                        },
                    )

                }
                // Estate list
                Column(modifier = Modifier.fillMaxWidth(0.3f)) {
                    EstateListScreen(navController = navController, windowSizeClass = windowSizeClass)
                }
                // Details or Map view
                Column(modifier = Modifier.fillMaxSize()) {

                    if (isMap) {
                        MapScreen(navController = navController, windowSizeClass = windowSizeClass)
                    }else{
                        EstateDetailScreen(navController = navController, windowSizeClass = windowSizeClass)
                    }
                }
            }
        }
        else -> {
            // Fallback for other sizes, default to Compact
            Column {
                EstateListScreen(navController = navController, windowSizeClass = windowSizeClass)
                EstateDetailScreen(navController = navController, windowSizeClass = windowSizeClass)
                MapScreen(navController = navController, windowSizeClass = windowSizeClass)
            }
        }
    }
}
@ExperimentalMaterial3WindowSizeClassApi
@Composable
@Preview
fun TabletMainScreenPreview(){
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(360.dp, 640.dp) // Example dimensions for a phone screen
    )
    AppTheme {

    }
}