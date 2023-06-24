package com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter

@Composable
fun PictureCard(uri: Uri){
    Card(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16 / 9f)) {
        AsyncImage(model = uri, contentDescription = "imageUri:$uri")
    }
}