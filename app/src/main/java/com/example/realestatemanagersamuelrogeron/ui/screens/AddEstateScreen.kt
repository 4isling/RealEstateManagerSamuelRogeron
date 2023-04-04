package com.example.realestatemanagersamuelrogeron.ui.screens

import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
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
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import com.srogeron.testcompose.ui.composables.SelectTypeTextField


@Composable
fun AddEstateScreen(navController: NavController, addEstatesViewModel: AddEstateViewModel){
    val picker = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()){
        uri -> Log.d("AddEstateScreen", "Media selected: $uri")
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
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
            picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }
                ))
            SelectNumberOf(what = "Rooms")
            AddTextFieldNoIcon(what = "Estate Name")
            AddTextField(what = "address", icon = Icons.Default.LocationOn)
            AddTextField(what = "City", icon = Icons.Default.LocationOn)
            AddTextField(what = "PostalCode", icon = Icons.Default.LocationOn)
            SelectTypeTextField()

            Button(onClick = {

            }) {
                Row() {
                    Text(text = "Save")
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "SaveEstate btn",
                        modifier = Modifier.clickable(
                            onClick = {
                                addEstatesViewModel.saveNewEstate()
                            }
                        )
                        )
                }
            }
        }


    }

}

@Composable
fun SelectNumberOf(what: String){
    Column(modifier = Modifier.padding(16.dp)) {
        val count: MutableState<Int> = remember {
            mutableStateOf(value = 0)
        }
        Text(text = "$what :")
        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = {count.value--}, Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "remove")
            }
            Text(text = count.value.toString(), Modifier.padding(8.dp))
            IconButton(onClick = { count.value++ }, Modifier.size(32.dp)) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    }
}


@Composable
fun AddTextBox(what: String){
    Row {
        Text(text = "$what :")


    }
}


