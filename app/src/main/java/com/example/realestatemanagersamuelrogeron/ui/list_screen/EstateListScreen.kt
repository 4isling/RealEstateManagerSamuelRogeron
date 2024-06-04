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
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemBottomAppBar
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.EstateList
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.SortMenu
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.EstatesListViewModel
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.SortType

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
    val listEstateWithPictures = listOf(
        EstateWithPictures(
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
            listOf()
        ),
        EstateWithPictures(estate = Estate(
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
        ), pictures = listOf()),
        EstateWithPictures(estate = Estate(
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
        ), pictures = listOf())
        )

    AppTheme{
        EstateListScreen(
            estateListState = ListViewState.Success(listEstateWithPictures),
            onEstateItemClick = {},
        )
    }
}


@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current

}