package com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.utils.StaticMapUrlProvider

@Composable
fun EstateDetail(viewModel: EstateDetailViewModel, modifier: Modifier) {
    val estate by viewModel.estate.collectAsState()
    val pics by viewModel.estatePictures.collectAsState()
    val interestPoints by viewModel.estateInterestPoints.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column() {


            // List of Pictures
            PictureCardList(modifier = Modifier.fillMaxWidth(), pics = pics)

            // Type of Estate
            Text(text = estate.typeOfEstate)

            // Type of Offer
            Text(text = estate.typeOfOffer)
            if (estate.typeOfOffer == "Rent") {
                Row() {
                    Text(
                        text = "Rent at:",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = " ${estate.rent}€",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }

            } else if (estate.typeOfOffer == "Sell") {
                Row() {


                Text(
                    text = "Selling Price: ",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
                Text(
                    text = " ${estate.sellingPrice}€",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ) }
            } else if (estate.typeOfOffer == "Rent or Sell") {
                Text(text = "Rent Price: ${estate.rent}")
                Text(text = "Selling Price: ${estate.sellingPrice}")
            }

            // Surface
            Text(text = "Surface: ${estate.surface} sqm")

            // Number of Rooms
            Text(text = "Rooms: ${estate.nbRooms}")

            // Description
            Text(text = "Description: ${estate.description}")

            // List of String Items
            LazyColumn {
                items(interestPoints) { item ->
                    Card(modifier = Modifier.safeContentPadding()) {
                        Text(text = item.interestPointsName)
                    }
                }
            }

            // Address
            Text(text = "Address: ${estate.address}")
            Text(text = "City: ${estate.city}")
            Text(text = "Zip Code: ${estate.zipCode}")

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
                            AsyncImage(drawable,
                                contentDescription = "static map",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f)
                            )
                        }
                        .build()

                    LaunchedEffect(staticMapUrl) {
                        imageLoader.enqueue(request)
                    }*/
        }
    }
}
