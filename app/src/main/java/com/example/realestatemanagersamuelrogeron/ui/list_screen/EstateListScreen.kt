package com.example.realestatemanagersamuelrogeron.ui.list_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.FilterDialog
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemBottomAppBar
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.EstateList
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.EstatesListViewModel
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun EstateListScreen(
    viewModel: EstatesListViewModel = hiltViewModel(),
    navController: NavController,
    windowSizeClass: WindowSizeClass,
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
        onFilterChange = {
            viewModel.updateFilter(newFilter = it)
        },
        onClickMap = {
            navController.navigate(Screen.Map.route)
        },
        onClickSetting = {
            navController.navigate(Screen.Settings.route)
        },
        windowSizeClass = windowSizeClass,
    )
}

@Composable
fun EstateListScreen(
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onAddEstateClick: () -> Unit = {},
    onFilterChange: (EstateFilter) -> Unit = {},
    onClickMap: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> { //phone in portrait
            EstateListPhoneScreen(
                estateListState,
                onEstateItemClick,
                onAddEstateClick,
                onFilterChange,
                onClickMap,
                onClickSetting
            )
        }

        WindowWidthSizeClass.Medium -> { //largePhone or tablet in portrait

        }

        WindowWidthSizeClass.Expanded -> {  //tablet landscape
            EstateListTabletScreen(
                estateListState,
                onEstateItemClick,
                onFilterChange,
            )
        }
    }

}

@Composable
fun EstateListPhoneScreen(
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onAddEstateClick: () -> Unit = {},
    onFilterChange: (EstateFilter) -> Unit = {},
    onClickMap: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
) {
    var showFilterDialog by remember {
        mutableStateOf(false)
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
                        estateWithDetails = estateListState.estates,
                        contentPadding = innerPadding
                    )
                    if (showFilterDialog) {
                        FilterDialog(
                            onFilterChange = { newFilter -> onFilterChange(newFilter) },
                            initialFilter = estateListState.estateFilter,
                            onDismiss = {
                                showFilterDialog = false
                            }
                        )
                    }
                }

                is ListViewState.Error -> {
                    Log.i("EstateListScreen", "EstateListScreen: Error")
                    Text(text = (estateListState.exception))
                }
            }
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showFilterDialog = true
                },

                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                content = {
                    Icon(imageVector = RemIcon.Filter, contentDescription = "FilterFab")
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End
    )
}

@Composable
fun EstateListTabletScreen(
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onFilterChange: (EstateFilter) -> Unit = {},
) {
    var showFilterDialog by remember {
        mutableStateOf(false)
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            when (estateListState) {
                is ListViewState.Loading -> {
                    Log.d("EstateListScreen", "EstateListScreen: loading")
                    Text(text = "loading")
                }

                is ListViewState.Success -> {
                    Log.d("EstateListScreen", "EstateListScreen: Success")
                    EstateList(
                        onEstateItemClick = onEstateItemClick,
                        estateWithDetails = estateListState.estates,
                        contentPadding = PaddingValues(16.dp)
                    )
                    if (showFilterDialog) {
                        FilterDialog(
                            onFilterChange = { newFilter -> onFilterChange(newFilter) },
                            initialFilter = estateListState.estateFilter,
                            onDismiss = {
                                showFilterDialog = false
                            }
                        )
                    }
                }

                is ListViewState.Error -> {
                    Log.i("EstateListScreen", "EstateListScreen: Error")
                    Text(text = (estateListState.exception))
                }
            }
        }
    }
}
    @ExperimentalMaterial3WindowSizeClassApi
    @Preview(showBackground = true)
    @Composable
    fun EstateListPhoneScreenPreview() {
        val estateWithDetails = listOf(
            EstateWithDetails(
                estate = Estate(
                    estateId = 0,
                    title = "House 1",
                    typeOfEstate = "House",
                    typeOfOffer = "Sell",
                    etage = "1",
                    address = "superbe Address",
                    zipCode = "06000",
                    city = "Nice",
                    description = "Superbe maison avec une super description",
                    addDate = 1716815996,
                    nbRooms = 4,
                    price = 350000,
                    surface = 150,
                ),
                estatePictures = listOf(),
                estateInterestPoints = listOf()
            ),
            EstateWithDetails(
                estate = Estate(
                    estateId = 0,
                    title = "House 2",
                    typeOfEstate = "House",
                    typeOfOffer = "Sell",
                    etage = "1",
                    address = "superbe Address",
                    zipCode = "06000",
                    city = "Nice",
                    description = "Superbe maison avec une super description",
                    addDate = 1716715996,
                    sellDate = 1716725996,
                    nbRooms = 4,
                    price = 350000,
                    surface = 150,
                ), estatePictures = listOf(),
                estateInterestPoints = listOf()
            ),
            EstateWithDetails(
                estate = Estate(
                    estateId = 0,
                    title = "House 3",
                    typeOfEstate = "House",
                    typeOfOffer = "Sell",
                    etage = "1",
                    address = "superbe Address",
                    zipCode = "06000",
                    city = "Nice",
                    description = "Superbe maison avec une super description",
                    addDate = 1716805996,
                    nbRooms = 4,
                    price = 350000,
                    surface = 150,
                ), estatePictures = listOf(),
                estateInterestPoints = listOf()
            )
        )

        AppTheme {
            EstateListScreen(
                estateListState = ListViewState.Success(
                    estateWithDetails,
                    EstateFilter()
                ),
                onEstateItemClick = {},
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(360.dp, 640.dp)),

                )
        }
    }

    @Composable
    @Preview
    fun EstateListTabletScreenPreview() {

    }


    @Composable
    fun TopAppBarCompose() {
        val context = LocalContext.current

    }