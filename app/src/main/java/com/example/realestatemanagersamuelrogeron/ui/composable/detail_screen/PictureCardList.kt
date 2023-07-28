package com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel

@Composable
fun PictureCardList(
    modifier: Modifier,
    pics: List<EstatePictures>
){
    LazyRow() {
        items(pics){pic->
            PictureCard(
                uri = pic.pictureUri.toUri(),
                modifier =  modifier
            )
        }
    }
}