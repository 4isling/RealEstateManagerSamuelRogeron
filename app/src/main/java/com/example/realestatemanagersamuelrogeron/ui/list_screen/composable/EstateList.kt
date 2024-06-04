package com.example.realestatemanagersamuelrogeron.ui.list_screen.composable

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.EstateItem
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme


@Composable
fun EstateList(
    contentPadding: PaddingValues,
    estateWithPictureList: List<EstateWithPictures>,
    onEstateItemClick: (Long) -> Unit,
){
    val lazyListState = rememberLazyListState()

    if (estateWithPictureList.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(estateWithPictureList) { estateWithPic ->
                Log.i(
                    ContentValues.TAG,
                    "EstateList: EstateWithPic = ${estateWithPic.estate}+ ${estateWithPic.pictures.size}"
                )
                EstateItem(
                    entry = estateWithPic.estate,
                    onEstateItemClick = onEstateItemClick,
                    media = estateWithPic.pictures,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
    else {
        Text(
            text = "Nothing to show",
        )
    }
}

@Preview
@Composable
fun EstateListPreview(){
    AppTheme {
        Surface(modifier = Modifier
            .width(360.dp)
            .height(640.dp)) {
            EstateList(
                contentPadding = PaddingValues(4.dp),
                estateWithPictureList = listOf(
                    EstateWithPictures(estate = Estate(
                        estateId = 0,
                        title = "La forge D'Entre Mont",
                        typeOfOffer = "Price",
                        typeOfEstate = "House",
                        etage = "2",
                        address = "24 Route De La Vallée",
                        zipCode = "06910",
                        region = "PACA",
                        country = "France",
                        city = "Pierrefeu",
                        description = "",
                        addDate = 0,
                        sellDate = 0,
                        agent = "",
                        price = 250000,
                        surface = 200,
                        nbRooms = 4,
                        status = true,
                    ), pictures = listOf()),
                    EstateWithPictures(estate = Estate(
                        estateId = 0,
                        title = "La forge D'Entre Mont",
                        typeOfOffer = "Price",
                        typeOfEstate = "House",
                        etage = "2",
                        address = "24 Route De La Vallée",
                        zipCode = "06910",
                        region = "PACA",
                        country = "France",
                        city = "Pierrefeu",
                        description = "",
                        addDate = 0,
                        sellDate = 0,
                        agent = "",
                        price = 250000,
                        surface = 200,
                        nbRooms = 4,
                        status = true,
                    ), pictures = listOf()),
                    EstateWithPictures(estate = Estate(
                        estateId = 0,
                        title = "La forge D'Entre Mont",
                        typeOfOffer = "Price",
                        typeOfEstate = "House",
                        etage = "2",
                        address = "24 Route De La Vallée",
                        zipCode = "06910",
                        region = "PACA",
                        country = "France",
                        city = "Pierrefeu",
                        description = "",
                        addDate = 0,
                        sellDate = 0,
                        agent = "",
                        price = 250000,
                        surface = 200,
                        nbRooms = 4,
                        status = true,
                    ), pictures = listOf()),
                )
            ) {

            }
        }
    }
}