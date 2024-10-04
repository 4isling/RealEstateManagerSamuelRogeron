package com.example.realestatemanagersamuelrogeron.ui.list_screen.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.EstateItem
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme


@Composable
fun EstateList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    estateWithDetails: List<EstateWithDetails>,
    isEuro: Boolean,
    onEstateItemClick: (Long) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    if (estateWithDetails.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(estateWithDetails) { estateWithDetails ->
                EstateItem(
                    entry = estateWithDetails,
                    isEuro = isEuro,
                    onEstateItemClick = onEstateItemClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Nothing to show",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview
@Composable
fun EstateListPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .width(360.dp)
                .height(640.dp)
        ) {
            EstateList(
                isEuro = false,
                contentPadding = PaddingValues(4.dp),
                estateWithDetails =
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
                )
            ) {

            }
        }
    }
}