package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EstateItem(
    modifier: Modifier = Modifier,
    entry: EstateWithDetails,
    isEuro: Boolean,
    onEstateItemClick: (Long) -> Unit = {},
) {
    val padding = 4.dp
    Card(
        modifier = modifier
            .clickable { onEstateItemClick(entry.estate.estateId) }
            .padding(padding),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {
                EstateImage(entry.estatePictures)
                Spacer(modifier = Modifier.width(padding))
                EstateDetails(entry.estate, isEuro = isEuro)
            }
        }
    }
}

@Composable
private fun EstateImage(media: List<EstateMedia>) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .height(130.dp)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)), // Clip the content to the rounded corners
        contentAlignment = Alignment.Center
    ) {
        if (media.isNotEmpty()) {
            MediaCardList(
                mediaList = media,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp) // Add some padding inside the box
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
                contentDescription = "No media",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .size(100.dp)
            )
        }
    }
}

@Composable
private fun EstateDetails(estate: Estate,isEuro: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(
            text = estate.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.onPrimary,
                    RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        AddressInfo(estate)
        Spacer(modifier = Modifier.height(4.dp))
        EstateAttributes(estate, isEuro = isEuro)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddressInfo(estate: Estate) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onPrimary,
                RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(estate.address, estate.zipCode, estate.city, estate.region, estate.country)
                .filter { it.isNotBlank() }
                .forEachIndexed { index, text ->
                    if (index > 0) Text(
                        ", ",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
        }
    }
}

@Composable
private fun EstateAttributes(estate: Estate, isEuro: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AttributeBox(text = "${estate.surface} m²")
        PriceBox(
            price = estate.price,
            offerType = estate.typeOfOffer,
            isEuro = isEuro
        )
    }
}

@Composable
private fun AttributeBox(text: String) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun PriceBox(price: Int, offerType: String, isEuro: Boolean) {
    Box(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.secondary,
                RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        PriceText(
            estatePrice = price,
            toEuro = isEuro,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            offer = offerType
        )
    }
}

@Preview
@Composable
fun EstateItemPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .height(670.dp)
                .width(360.dp)
        ) {
            EstateItem(
                isEuro = false,
                entry =
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
                    ),
                    estatePictures = listOf(
                        EstateMedia(
                            0,
                            0,
                            "/storage/self/Pictures/IMG_20230906_164952.jpg",
                            "image/",
                            "Facade"
                        )
                    ),
                    estateInterestPoints = predefinedInterestPoints,
                )
            )

        }
    }
}

fun NavController.safeNavigate(directions: String) {
    currentBackStackEntry?.arguments?.putBoolean("allowRecreation", true)
    try {
        navigate(directions)
    } catch (e: Exception) {
        Log.e("Navigation", e.toString())
    }
}