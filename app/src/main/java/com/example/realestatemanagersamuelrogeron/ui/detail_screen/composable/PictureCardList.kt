package com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCard

@Composable
fun PictureCardList(
    modifier: Modifier,
    pics: List<EstateMedia>
){
    LazyRow() {
        items(pics){pic->
            PictureCard(
                uri = pic.uri.toUri(),
                modifier =  modifier
            )
        }
    }
}