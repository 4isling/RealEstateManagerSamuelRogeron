package com.example.realestatemanagersamuelrogeron.ui.screens

import androidx.compose.foundation.Image
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
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel


@Composable
fun AddEstateScreen(navController: NavController, addEstatesViewModel: AddEstateViewModel){
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painterResource(id = ), contentDescription = "imagesAjouter")
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
                    Icon(imageVector = Icons.Default.Check, contentDescription = "SaveEstate btn")
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


