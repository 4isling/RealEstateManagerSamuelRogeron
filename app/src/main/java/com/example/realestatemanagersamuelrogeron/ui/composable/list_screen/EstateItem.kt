package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.event.ListScreenEvent
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCard
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCardList
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel

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
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(8.dp)
        ) {
            if(pic.isNotEmpty()){
                PictureCard(pic[0].pictureUri.toUri())
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