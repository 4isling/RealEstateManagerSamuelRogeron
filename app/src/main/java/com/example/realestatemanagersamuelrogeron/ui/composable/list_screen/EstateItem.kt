package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCard
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen

@Composable
fun EstateItem(
    pic: List<EstatePictures>,
    entry: Estate,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                println("id : " + entry.id)
                navController.safeNavigate("${Screen.EstateDetail.route}/${entry.id}")
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            if (pic.isNotEmpty()) {
                PictureCard(
                    uri = pic[0].pictureUri.toUri(),
                    modifier = Modifier.padding(1.dp)
                )
            }
            Column() {
                Text(entry.title.toString())
                Text(text = entry.nbRooms.toString() + " rooms")
                Text(entry.address.toString())
            }
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