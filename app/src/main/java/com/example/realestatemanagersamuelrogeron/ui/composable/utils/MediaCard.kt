package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.utils.FileTypeHelper

@Composable
fun MediaCard(filePath: String,
              modifier: Modifier = Modifier,
              isSuppressButtonEnable: Boolean = false,
              onSuppressClick: (Uri) -> Unit = {}) {
    val uri = Uri.parse(filePath)
    val context = LocalContext.current
    val mimeType = FileTypeHelper.getMimeType(context = context, uri = uri)
    when {
        mimeType?.startsWith("image") == true -> PictureCard(uri = uri, modifier = modifier, isSuppressButtonEnable = isSuppressButtonEnable, onSuppressClick = onSuppressClick)
        mimeType?.startsWith("video") == true -> VideoCard(videoUri = uri, modifier = modifier, isSuppressButtonEnable = isSuppressButtonEnable,onSuppressClick = onSuppressClick)
        else -> Text("Unsupported media type", modifier = modifier.padding(16.dp))
    }
}

@Composable
fun MediaCard(filePath: String,
              modifierImage: Modifier = Modifier,
              modifierVideo: Modifier = Modifier,
              isSuppressButtonEnable: Boolean = false,
              onSuppressClick: (Uri) -> Unit = {}) {
    val uri = Uri.parse(filePath)
    val context = LocalContext.current
    val mimeType = FileTypeHelper.getMimeType(context = context, uri = uri)
    when {
        mimeType?.startsWith("image") == true ->
            PictureCard(uri = uri, modifier = modifierImage, isSuppressButtonEnable = isSuppressButtonEnable, onSuppressClick = onSuppressClick)
        mimeType?.startsWith("video") == true ->
            VideoCard(videoUri = uri, modifier = modifierVideo, isSuppressButtonEnable = isSuppressButtonEnable, onSuppressClick = onSuppressClick)
        else -> Text("Unsupported media type", modifier = Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun MediaCardPreview(){
    MediaCard(filePath = "",
        modifier = Modifier.height(200.dp)
            .width(100.dp)
    )
}
