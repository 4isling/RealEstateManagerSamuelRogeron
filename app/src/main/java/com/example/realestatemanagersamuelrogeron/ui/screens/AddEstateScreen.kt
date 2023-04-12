package com.example.realestatemanagersamuelrogeron.ui.screens

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.AddTextField
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.AddTextFieldNoIcon
import com.example.realestatemanagersamuelrogeron.ui.composable.add_screen.DescriptionTextField
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.srogeron.testcompose.ui.composables.SelectTextField


@Composable
fun AddEstateScreen(navController: NavController, addEstatesViewModel: AddEstateViewModel) {
    val picker =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) { uri ->
            Log.d("AddEstateScreen", "Media selected: $uri")
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
                .safeContentPadding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painterResource(id = R.drawable.baseline_add_a_photo_24),
                contentDescription = "imagesAdd",
                modifier =
                Modifier
                    .clickable(
                        true,
                        onClick = {
                            picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        }
                    )
            )
            SelectNumberOf(what = "Rooms")
            AddTextFieldNoIcon(
                what = "Estate Name",
                modifier = Modifier.fillMaxWidth()
            )
            AddTextField(
                uText = "",
                what = "Address",
                icon = Icons.Default.LocationOn,
                modifier = Modifier.fillMaxWidth()
            )
            Row() {
                AddTextField(
                    uText = "",
                    what = "City",
                    icon = Icons.Default.LocationOn,
                    modifier = Modifier.fillMaxWidth(0.4f)
                )
                AddTextFieldNoIcon(
                    uText = "",
                    what = "PostalCode",
                    modifier = Modifier.fillMaxWidth()
                )
            }
            SelectTextField(
                what = "Type of Offer",
                listOf("Rent", "Sell", "Rent or Sell")
            )
            SelectTextField(
                what = "Estate Type",
                choices = listOf("House", "Apartment", "Garage", "Land")
            )
            DescriptionTextField(
                what = "Description",
                icon = Icons.Default.Edit
            )

            Button(
                onClick = {
                addEstatesViewModel.saveNewEstate()
            }
            ) {
                Row() {
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

@Composable
fun SelectNumberOf(what: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        val count: MutableState<Int> = remember {
            mutableStateOf(value = 0)
        }
        Text(text = "$what :")
        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { count.value-- }, Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "remove")
            }
            Text(text = count.value.toString(), Modifier.padding(8.dp))
            IconButton(onClick = { count.value++ }, Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
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


