package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCardList
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.PriceText
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemDetailTopAppBar
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.DescriptionBox
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.LocationBox
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.DetailViewState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@Composable
fun EstateDetailPhoneScreen(
    estateWithDetail: EstateWithDetails,
    onBackPress: () -> Unit = {},
    onEdit: (Long) -> Unit = {},
    isEuro: Boolean
) {
    Scaffold(topBar = {
        RemDetailTopAppBar(
            title = estateWithDetail.estate.title,
            modifier = Modifier,
            onBackPress = {
                onBackPress()
            },
            onEditIconClick = {
                onEdit(estateWithDetail.estate.estateId)
            },
        )
    }, content = { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                MediaCardList(
                    mediaList = estateWithDetail.estatePictures,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                )
            }
            item {
                PriceText(
                    estatePrice = estateWithDetail.estate.price,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    toEuro = isEuro,
                    offer =  estateWithDetail.estate.typeOfOffer,
                )
            }
            item {
                InterestPointsBox(
                    modifier = Modifier,
                    interestPointsList = estateWithDetail.estateInterestPoints,
                    editEnable = false,
                    )
            }
            item {
                LocationBox(
                    address = estateWithDetail.estate.address,
                    city = estateWithDetail.estate.city,
                    region = estateWithDetail.estate.region,
                    country = estateWithDetail.estate.country,
                    modifier = Modifier.padding(4.dp),
                    staticMap = estateWithDetail.estate.staticMapUrl //@TODO
                )
            }
            item {
                DescriptionBox(
                    description = estateWithDetail.estate.description,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }
        }
    })
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun DetailPhoneScreenPreview() {
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(360.dp, 640.dp) // Example dimensions for a phone screen
    )
    AppTheme {
        Surface(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(1824.dp, 1600.dp)
        ) {
            EstateDetailScreenContent(windowSizeClass = windowSizeClass,
                uiState = DetailViewState.Success(
                    estate = EstateWithDetails(
                        Estate(
                            estateId = 0,
                            title = "La forge D'Entre Mont",
                            typeOfOffer = "Rent",
                            typeOfEstate = "House",
                            etage = "2",
                            address = "address",
                            zipCode = "",
                            city = "Pierre-Feu",
                            description = "oé c'est une description",
                            addDate = 0,
                            sellDate = 0,
                            agent = "",
                            price = 2500,
                            surface = 200,
                            nbRooms = 4,
                            status = true,
                        ),
                        estatePictures = listOf(EstateMedia(0, 0, "HURI", "", "")),
                        estateInterestPoints = predefinedInterestPoints,
                    ), isEuro = false
                ),
                onBackPress = {},
                onEdit = {})
        }
    }
}
