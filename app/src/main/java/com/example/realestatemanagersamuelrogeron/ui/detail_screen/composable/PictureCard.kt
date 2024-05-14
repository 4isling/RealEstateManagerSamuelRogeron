package com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen

import android.net.Uri
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun PictureCard(uri: Uri,modifier: Modifier){
    Card(modifier = modifier
        .aspectRatio(16 / 9f)) {
        AsyncImage(model = uri, contentDescription = "imageUri:$uri")
    }
}