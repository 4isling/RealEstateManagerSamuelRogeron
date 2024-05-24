package com.example.realestatemanagersamuelrogeron.ui.list_screen.composable

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCard
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen

@Composable
fun EstateItem(
    pic: List<EstateMedia>,
    entry: Estate,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                println("id : " + entry.estateId)
                navController.safeNavigate("${Screen.EstateDetail.route}/${entry.estateId}")
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            if (pic.isNotEmpty()) {
                MediaCard(
                    filePath = if (pic.isNotEmpty()) {
                        pic[0].uri
                    } else {
                        ""
                    },
                    modifierImage = Modifier
                        .height(156.dp)
                        .width(156.dp),
                    modifierVideo = Modifier
                        .height(156.dp)
                        .width(156.dp),
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

@Composable
fun EstateItem(
    pic: List<EstateMedia>,
    entry: Estate,
    onEstateItemClick: (Long) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                println("id : " + entry.estateId)
                onEstateItemClick(entry.estateId)
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            if (pic.isNotEmpty()) {
                MediaCard(
                    filePath = pic[0].uri,
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

@Preview
@Composable
fun EstateItemPreview() {
    EstateItem(
        pic = listOf(EstateMedia(0, 0, "/storage/self/Pictures/IMG_20230906_164952.jpg", "Facade")),
        entry = Estate(
            estateId = 0,
            title = "La forge D'Entre Mont",
            typeOfOffer = "",
            typeOfEstate = "House",
            etage = "2",
            address = "address",
            zipCode = "",
            city = "",
            description = "",
            addDate = 0,
            sellDate = 0,
            agent = "",
            sellingPrice = 250000,
            rent = null,
            surface = 200,
            nbRooms = 4,
            status = true,
        ),
        navController = rememberNavController(),
    )
}

fun NavController.safeNavigate(directions: String) {
    currentBackStackEntry?.arguments?.putBoolean("allowRecreation", true)
    try {
        navigate(directions)
    } catch (e: Exception) {
        Log.e("Navigation", e.toString())
    }
}