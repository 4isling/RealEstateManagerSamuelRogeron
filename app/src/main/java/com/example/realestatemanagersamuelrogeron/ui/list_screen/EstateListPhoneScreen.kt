package com.example.realestatemanagersamuelrogeron.ui.list_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.FilterDialog
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemBottomAppBar
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.EstateList
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun EstateListPhoneScreen(
    modifier: Modifier = Modifier,
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
                    Box(modifier = modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)) {
                        EstateList(
                            onEstateItemClick = onEstateItemClick,
                            estateWithDetails = estateListState.estates,
                            isEuro = estateListState.isEuro,
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
                EstateFilter(),
                isEuro = false
            ),
            onEstateItemClick = {},
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(360.dp, 640.dp)),

            )
    }
}
