package com.example.realestatemanagersamuelrogeron.ui.list_screen

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemBottomAppBar
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.EstateList
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.SortMenu
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.EstatesListViewModel
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.utils.SortType


/*@OptIn(ExperimentalMaterial3Api::class)
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
                        scope.launch {
                            drawerState.open()
                        }
                        Log.i(TAG, "EstateListScreen: DrawerClick")
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer Icon")

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
        content = { innerPadding ->

            SortMenu(
                sortOptions,
                onSortOptionSelected = {
                    viewModel.onSortTypeValueChange(it)
                }
            )
            Box(modifier = Modifier.fillMaxSize()) {
                DrawerListContent(viewModel, navController, drawerState)
                when (viewState) {
                    is ListViewState.Loading -> {
                        Log.i(TAG, "EstateListScreen: loading")
                        Text(text = "loading")
                    }

                    is ListViewState.Success -> {
                        Log.i(TAG, "EstateListScreen: Success")
                        EstateList(
                            innerPadding,
                            navController,
                            viewModel,
                        )
                    }

                    is ListViewState.Error -> {
                        Log.i(TAG, "EstateListScreen: Error")
                        Text(text = (viewState as ListViewState.Error).exception)
                    }

                }
            }
        },
        bottomBar = {
                    RemBottomAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},

                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                content = {
                    SortMenu(
                        sortOptions,
                        onSortOptionSelected = {
                            viewModel.onSortTypeValueChange(it)
                        }
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
}
*/
@Composable
fun EstateListScreen(
    viewModel: EstatesListViewModel = hiltViewModel(),
    navController: NavController,
) {
    val TAG = "EstateListScreen:"
    var sortMenuExpend by remember {
        mutableStateOf(false)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        "Map",
    )

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    EstateListScreen(
        estateListState = uiState,
        onEstateItemClick = {
            navController.safeNavigate("${Screen.EstateDetail.route}/${it}")
        },
        onAddEstateClick = {
            navController.navigate(Screen.AddEstate.route)
        },
        onSortChange = {
            viewModel.onSortTypeValueChange(it)
        },
        onClickMap = {
            navController.navigate(Screen.Map.route)
        },
        onClickSetting = {

        },
    )
}

@Composable
fun EstateListScreen(
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onAddEstateClick: () -> Unit = {},
    onSortChange: (SortType) -> Unit = {},
    onClickMap: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
) {
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
        bottomBar = {
            RemBottomAppBar(
                selectedScreen = "List",
                onScreenSelected = onClickMap,
                onClickSetting = onClickSetting,
                onClickAdd = onAddEstateClick,
            )
        },
        content = { innerPadding ->
            when (estateListState) {
                is ListViewState.Loading -> {
                    Log.d("EstateListScreen", "EstateListScreen: loading")
                    Text(text = "loading")
                }

                is ListViewState.Success -> {
                    Log.d("EstateListScreen", "EstateListScreen: Success")
                    EstateList(
                        onEstateItemClick = onEstateItemClick,
                        estateWithPictureList = estateListState.estates,
                        contentPadding = innerPadding
                    )
                }

                is ListViewState.Error -> {
                    Log.i("EstateListScreen", "EstateListScreen: Error")
                    Text(text = (estateListState.exception))
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},

                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                content = {
                    SortMenu(
                        sortOptions,
                        onSortOptionSelected = {
                            onSortChange(it)
                        }
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
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