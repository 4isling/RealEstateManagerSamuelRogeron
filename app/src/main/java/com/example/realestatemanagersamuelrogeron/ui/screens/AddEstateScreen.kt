package com.example.realestatemanagersamuelrogeron.ui.screens

import android.content.ClipData.Item
import android.net.Uri
import android.util.Log
import android.view.RoundedCorner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.*
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.srogeron.testcompose.ui.composables.SelectTextField


@Composable
fun AddEstateScreen(navController: NavController, addEstatesViewModel: AddEstateViewModel) {
    var pTitle by remember {
        mutableStateOf("")
    }
    var pTypeOfEstate by remember {
        mutableStateOf("")
    }
    var pTypeOfOffer by remember {
        mutableStateOf("")
    }
    var pSellingPrice by remember {
        mutableStateOf("")
    }
    var pRent by remember {
        mutableStateOf("")
    }
    var pSurface by remember {
        mutableStateOf("")
    }
    var pNbRooms by remember {
        mutableStateOf("")
    }
    var pEtage by remember {
        mutableStateOf("")
    }
    var pAddress by remember {
        mutableStateOf("")
    }
    var pZipCode by remember {
        mutableStateOf("")
    }
    var pCity by remember {
        mutableStateOf("")
    }
    var pDescription by remember {
        mutableStateOf("")
    }
    var pListOfPictures by remember {
        mutableStateOf<List<Uri>>(value = emptyList())
    }
    var pListOfInterestPoints by remember {
        mutableStateOf<List<String>>(value = emptyList())
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .safeContentPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddListOfPictures()
            }
            item {
                SelectNumberOf(what = "Rooms")
                AddTextFieldNoIcon(
                    what = "Estate Name",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                AddTextField(
                    uText = "",
                    what = "Address",
                    icon = Icons.Default.LocationOn,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    AddTextField(
                        uText = "",
                        what = "City",
                        icon = Icons.Default.LocationOn,
                        modifier = Modifier.fillMaxWidth(0.50f)
                    )
                    AddTextFieldNoIcon(
                        uText = "",
                        what = "ZipCode",
                        modifier = Modifier.fillMaxWidth(0.50f)
                    )
                }
            }

            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                    ) {
                    SelectTextField(
                        what = "Estate Type",
                        choices = listOf("House", "Apartment", "Garage", "Land"),
                        modifier = Modifier.fillMaxWidth(0.50f)
                    )
                    SelectTextField(
                        what = "Type of Offer",
                        choices =  listOf("Rent", "Sell", "Rent or Sell"),
                        modifier = Modifier.fillMaxWidth(0.50f)
                    )
                }
            }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                ){
                    AddNumberTextField(
                        modifier = Modifier.fillMaxWidth(1f),
                        what = "Price",
                        uText = "",
                        resId = (R.drawable.baseline_attach_money_24)
                    )
                    AddNumberTextField(
                        modifier = Modifier.fillMaxWidth(1f),
                        what = "Rent",
                        uText = "",
                        resId = (R.drawable.baseline_attach_money_24)
                    )
                }
            }
            item {
                AddNumberTextField(modifier = Modifier.fillMaxWidth(), what = "Surface", uText = "")
            }
            item{
                AddNumberTextField(modifier = Modifier.fillMaxWidth(1f), what = "Etage", uText = "")
            }

            item {
                DescriptionTextField(
                    what = "Description",
                    icon = Icons.Default.Edit
                )
            }
            item {
                InterestPointsTextField(modifier = Modifier.fillMaxWidth())
            }
            item {
                Button(
                    onClick = {
                        addEstatesViewModel.saveNewEstate()
                    }
                ) {
                    Row(modifier = Modifier.clickable {

                    }
                    ) {
                        Text(text = "Save")
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "SaveEstate btn",
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectNumberOf(what: String) {
    Surface(
        shape = RoundedCornerShape(25.dp),
        border = BorderStroke(2.dp, Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val count: MutableState<Int> = remember {
                mutableStateOf(value = 0)
            }
            Text(text = "$what :")
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = { count.value-- },
                    Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_remove_circle_24),
                        contentDescription = "remove"
                    )
                }
                Text(
                    text = count.value.toString(),
                    Modifier.padding(8.dp)
                )
                IconButton(
                    onClick = { count.value++ },
                    Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AddCircle,
                        contentDescription = "add"
                    )
                }
            }
        }
    }
}

@Composable
fun AddTextBox(what: String) {
    Row {
        Text(text = "$what :")


    }
}


