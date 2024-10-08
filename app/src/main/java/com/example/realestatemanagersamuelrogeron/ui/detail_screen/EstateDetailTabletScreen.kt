package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCardList
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.PriceText
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.TabletLocationBox
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@Composable
fun EstateDetailTabletScreen(
    estateWithDetails: EstateWithDetails,
    onEdit: (Long) -> Unit,
    isEuro: Boolean,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title and Edit Button
            TitleRow(estateWithDetails, onEdit)
            Spacer(modifier = Modifier.height(16.dp))

            // Main content area
            Row(modifier = Modifier.weight(1f)) {
                // Left column: Images, Price, Surface, Description
                Column(modifier = Modifier.weight(0.6f)) {
                    MediaCardList(estateWithDetails.estatePictures)
                    Spacer(modifier = Modifier.height(16.dp))
                    PriceAndSurfaceRow(estateWithDetails, isEuro)
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionCard(estateWithDetails.estate.description)
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Right column: Location and Interest Points
                Column(modifier = Modifier.weight(0.4f)) {
                    TabletLocationBox(
                        modifier = Modifier.weight(1f),
                        address = estateWithDetails.estate.address,
                        city = estateWithDetails.estate.city,
                        region = estateWithDetails.estate.region,
                        country = estateWithDetails.estate.country,
                        staticMap = estateWithDetails.estate.staticMapUrl
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    InterestPointsBox(
                        modifier = Modifier.weight(1f).fillMaxWidth().wrapContentHeight(),
                        interestPointsList = estateWithDetails.estateInterestPoints
                    )
                }
            }
        }
    }
}

@Composable
private fun TitleRow(estateWithDetails: EstateWithDetails, onEdit: (Long) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            text = estateWithDetails.estate.title,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
        FloatingActionButton(
            onClick = { onEdit(estateWithDetails.estate.estateId) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(imageVector = RemIcon.Edit, contentDescription = "Edit Button")
        }
    }
}

@Composable
private fun PriceAndSurfaceRow(estateWithDetails: EstateWithDetails, isEuro: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PriceText(
            estatePrice = estateWithDetails.estate.price,
            toEuro = isEuro,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier,
            offer = estateWithDetails.estate.typeOfOffer
        )
        AttributeChip(text = "${estateWithDetails.estate.surface} m²")
    }
}

@Composable
private fun DescriptionCard(description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AttributeChip(text: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun DetailTabletScreenPreview() {
    AppTheme {
        EstateDetailTabletScreen(
            estateWithDetails = EstateWithDetails(
                Estate(
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
            onEdit = {  },
            isEuro = true
        )
    }
}