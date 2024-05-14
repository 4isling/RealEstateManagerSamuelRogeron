package com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.state.DetailState
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.PictureCardList

@Composable
fun EstateDetail(
    detailState: DetailState,
    modifier: Modifier,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            // List of Pictures
            PictureCardList(modifier = Modifier.fillMaxWidth(), pics = detailState.pictures)

            // Type of Estate
            Text(text = detailState.estate.typeOfEstate)

            // Type of Offer
            Text(text = "Price")
            HorizontalDivider()
            if (detailState.estate.typeOfOffer == "Rent") {
                Row() {
                    Text(
                        text = "Rent at:",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = " ${detailState.estate.rent}€",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            } else if (detailState.estate.typeOfOffer == "Sell") {
                Row() {
                    Text(
                        text = "Selling Price: ",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = " ${detailState.estate.sellingPrice}€",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            } else if (detailState.estate.typeOfOffer == "Rent or Sell") {
                Text(text = "Rent Price: ${detailState.estate.rent}")
                Text(text = "Selling Price: ${detailState.estate.sellingPrice}")
            }

            // Surface
            Text(text = "Surface: ${detailState.estate.surface} sqm")

            // Number of Rooms
            Text(text = "Rooms: ${detailState.estate.nbRooms}")

            // Description
            Text(text = "Description: ${detailState.estate.description}")

            // List of String Items
            LazyColumn {
                items(detailState.interestPoints) { item ->
                    Card(modifier = Modifier.safeContentPadding()) {
                        Text(text = item.interestPointsName)
                    }
                }
            }

            // Address
            Text(text = stringResource(R.string.location),
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
            HorizontalDivider(
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 16.dp,
                    bottom = 16.dp,
                    end = 16.dp
                ),
                thickness = 2.dp,
                color = Color.DarkGray
            )
            Text(text = stringResource(R.string.address, detailState.estate.address))
            Text(text = stringResource(R.string.city, detailState.estate.city))
            Text(text = stringResource(R.string.zip_code, detailState.estate.zipCode))

            // Static Map
            /*
                    val staticMapUrl = estate.lat?.let { estate.lng?.let { it1 ->
                        StaticMapUrlProvider.getStaticMapUrl(it,
                            it1
                        )
                    } }
                    val context = LocalContext.current
                    val imageLoader = ImageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .crossfade(true)
                        .data(staticMapUrl)
                        .target { drawable ->
                            AsyncImage(model = drawable,
                                contentDescription = "static map",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                            )
                        }
                        .build()

                    LaunchedEffect(staticMapUrl) {
                        imageLoader.enqueue(request)
                    }
            */
        }
    }
}
