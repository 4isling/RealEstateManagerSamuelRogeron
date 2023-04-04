package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

@Composable
fun EstateItem(
    entry: Estate,
    navController: NavController,
    viewModel: EstatesListViewModel
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                navController.navigate(Screen.EstateDetail.route)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(id = R.drawable.baseline_cottage_24),
            contentDescription = "EstateImage"
        )
        Column() {
            Text(entry.title.toString())
            Text(text = entry.nbRooms.toString() + " rooms")
            Text(entry.address.toString())
        }
    }
}