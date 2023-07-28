package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AddPictureItem(uri: Uri) {
    Box(
        modifier = Modifier
            .fillMaxSize(0.1f),

    ){

        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}