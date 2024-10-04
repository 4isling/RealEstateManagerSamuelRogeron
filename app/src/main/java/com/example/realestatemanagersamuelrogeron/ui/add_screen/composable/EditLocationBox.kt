package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@Composable
fun EditLocationBox(
    uiState: AddEstateState,
    onCountryValueChange: (String) -> Unit,
    onRegionValueChange: (String) -> Unit,
    onCityValueChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onAddressValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.wrapContentSize(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
        ) {
            Row {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    isError = uiState.countryError,
                    value = uiState.estateWithDetails.estate.country,
                    onValueChange = onCountryValueChange,
                    placeholder = { Text(text = "Enter the country") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = uiState.estateWithDetails.estate.region,
                    onValueChange = onRegionValueChange,
                    placeholder = { Text(text = "Enter the region") }
                )
            }

            Row {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = uiState.estateWithDetails.estate.city,
                    onValueChange = onCityValueChange,
                    isError = uiState.cityError,
                    placeholder = { Text(text = "Enter the city") }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .padding(8.dp),
                    value = uiState.estateWithDetails.estate.zipCode,
                    isError = uiState.zipCodeError,
                    onValueChange = onZipCodeChange,
                    placeholder = { Text(text = "Enter the ZipCode") }
                )
            }

            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp),
                value = uiState.estateWithDetails.estate.address,
                isError = uiState.addressError,
                onValueChange = onAddressValueChange,
                placeholder = { Text(text = "Enter the Address") }
            )
        }
    }
}

@Preview(widthDp = 2000, heightDp = 1600 )
@Composable
fun EditLocationBoxPreview() {
    AppTheme {
        Surface() {
            EditLocationBox(
                uiState = AddEstateState(
                    estateWithDetails = EstateWithDetails(
                        estate = Estate(
                            estateId = 0,
                            title = "La forge D'Entre Mont",
                            typeOfOffer = "Rent",
                            typeOfEstate = "House",
                            etage = "2",
                            address = "Address",
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
                    ),
                ),
                onAddressValueChange = {},
                onCityValueChange = {},
                onCountryValueChange = {},
                onRegionValueChange = {},
                onZipCodeChange = {},
            )
        }
    }
}