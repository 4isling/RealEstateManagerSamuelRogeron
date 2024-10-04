package com.example.realestatemanagersamuelrogeron.ui.list_screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.FilterDialog
import com.example.realestatemanagersamuelrogeron.ui.list_screen.composable.EstateList
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun EstateListTabletScreen(
    modifier: Modifier = Modifier,
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onFilterChange: (EstateFilter) -> Unit = {},
) {
    var showFilterDialog by remember {
        mutableStateOf(false)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp, 8.dp, 8.dp, 8.dp),
        color = MaterialTheme.colorScheme.inversePrimary,
        shape = RoundedCornerShape(8.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(8.dp)) {
                when (estateListState) {
                    is ListViewState.Loading -> {
                        Log.d("EstateListScreen", "EstateListScreen: loading")
                        Text(text = "loading")
                    }

                    is ListViewState.Success -> {
                        Log.d("EstateListScreen", "EstateListScreen: Success")

                        EstateList(
                            modifier = Modifier.fillMaxSize(),
                            onEstateItemClick = onEstateItemClick,
                            estateWithDetails = estateListState.estates,
                            contentPadding = PaddingValues(16.dp),
                            isEuro = estateListState.isEuro,
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

                    else -> {
                        Log.i("EstateListScreen", "EstateListScreen: Error")
                        Text(text = ("error"))
                    }
                }
            }

            // FloatingActionButton placed at the bottom end of the screen
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                onClick = {
                    showFilterDialog = true
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                content = {
                    Icon(imageVector = RemIcon.Filter, contentDescription = "FilterFab")
                }
            )
        }
    }
}

@Composable
@Preview(widthDp = 422, heightDp = 800)
fun EstateListTabletScreenPreview() {
    AppTheme {
        EstateListTabletScreen(
            estateListState = ListViewState.Success(
                estates =
                listOf(
                    EstateWithDetails(
                        estate = Estate(
                            estateId = 0,
                            title = "La forge D'Entre Mont",
                            typeOfOffer = "Price",
                            typeOfEstate = "House",
                            etage = "2",
                            address = "24 Route De La Vallée",
                            zipCode = "06910",
                            region = "PACA",
                            country = "France",
                            city = "Pierrefeu",
                            description = "",
                            addDate = 0,
                            sellDate = 0,
                            agent = "",
                            price = 250000,
                            surface = 200,
                            nbRooms = 4,
                            status = true,
                        ), estatePictures = listOf(),
                        estateInterestPoints = listOf()
                    ),
                    EstateWithDetails(
                        estate = Estate(
                            estateId = 0,
                            title = "La forge D'Entre Mont",
                            typeOfOffer = "Price",
                            typeOfEstate = "House",
                            etage = "2",
                            address = "24 Route De La Vallée",
                            zipCode = "06910",
                            region = "PACA",
                            country = "France",
                            city = "Pierrefeu",
                            description = "",
                            addDate = 0,
                            sellDate = 0,
                            agent = "",
                            price = 250000,
                            surface = 200,
                            nbRooms = 4,
                            status = true,
                        ), estatePictures = listOf(),
                        estateInterestPoints = listOf()
                    ),
                    EstateWithDetails(
                        estate = Estate(
                            estateId = 0,
                            title = "La forge D'Entre Mont",
                            typeOfOffer = "Price",
                            typeOfEstate = "House",
                            etage = "2",
                            address = "24 Route De La Vallée",
                            zipCode = "06910",
                            region = "PACA",
                            country = "France",
                            city = "Pierrefeu",
                            description = "",
                            addDate = 0,
                            sellDate = 0,
                            agent = "",
                            price = 250000,
                            surface = 200,
                            nbRooms = 4,
                            status = true,
                        ), estatePictures = listOf(),
                        estateInterestPoints = listOf()

                    ),
                ), estateFilter = EstateFilter(),
                isEuro = false
            ),
            onEstateItemClick = {},
        )
    }
}