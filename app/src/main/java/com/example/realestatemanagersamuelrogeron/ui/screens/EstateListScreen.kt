package com.example.realestatemanagersamuelrogeron.ui.screens

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.data.state.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.DrawerListContent
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.EstateList
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.SortMenu
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateListScreen(
    navController: NavController,
    viewModel: EstatesListViewModel = hiltViewModel()
) {
    val TAG = "EstateListScreen:"
    val viewState by viewModel.viewState.collectAsState()
    var sortMenuExpend by remember {
        mutableStateOf(false)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        "Map",
    )

    val sortOptions = listOf(
        SortType.Default,
        SortType.PriceDescend,
        SortType.PriceGrow,
        SortType.RentDescend,
        SortType.RentGrow
    )
    var selectedFilter by remember {
        mutableStateOf(sortOptions)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "RealEstateManager")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch{
                            drawerState.open()
                        }
                        Log.i(TAG, "EstateListScreen: DrawerClick")
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer Icon")
                        DrawerListContent(viewModel,navController,drawerState)
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search Estate"
                        )
                    }
                    SortMenu(
                        sortOptions,
                        onSortOptionSelected = {
                            viewModel.onSortTypeValueChange(it)
                        }
                    )
                    IconButton(onClick = {
                        Log.i(TAG, "EstateListScreen: nav to addEstate")
                        navController.navigate(Screen.AddEstate.route)
                        
                    }) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "addEstate")
                    }
                }
            )
        },
        content =  { innerPadding ->
            when (viewState) {
                is ListViewState.Loading -> {
                    Log.i(TAG, "EstateListScreen: loading")
                    Text(text = "loading")
                }

                is ListViewState.Success -> {
                    Log.i(TAG, "EstateListScreen: Success")
                    val isLoading by viewModel.isLoading.collectAsState()
                    val isRefreshing by viewModel.isRefreshing.collectAsState()
                    EstateList(
                        innerPadding,
                        navController,
                        viewModel,
                        isLoading,
                        isRefreshing
                    )
                }

                is ListViewState.Error -> {
                    Log.i(TAG, "EstateListScreen: Error")
                    Text(text = (viewState as ListViewState.Error).exception)
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
fun EstateListScreenPreview() {
}


@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current

}