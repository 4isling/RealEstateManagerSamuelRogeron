package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
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
        else -> Image(imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink), modifier = modifier, contentDescription = null)

    }
}

@Composable
fun MediaCard(estateMedia: EstateMedia,
              modifier: Modifier = Modifier,
              isSuppressButtonEnable: Boolean = false,
              onSuppressClick: (Uri) -> Unit = {}) {
    when {
        estateMedia.mimeType?.startsWith("image") == true -> PictureCard(uri = estateMedia.uri.toUri(), modifier = modifier, isSuppressButtonEnable = isSuppressButtonEnable, onSuppressClick = onSuppressClick)
        estateMedia.mimeType?.startsWith("video") == true -> VideoCard(videoUri = estateMedia.uri.toUri(), modifier = modifier, isSuppressButtonEnable = isSuppressButtonEnable,onSuppressClick = onSuppressClick)
        else -> Image(imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink), modifier = modifier, contentDescription = null)

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
        else -> Image(imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink), modifier = modifierImage, contentDescription = null)
    }
}

@Composable
fun MediaCardList(
    mediaList: List<EstateMedia>,
    modifier: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {}
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    if (mediaList.isNotEmpty()) {
        Box(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { change, dragAmount ->
                        if (dragAmount > 0) {
                            if (currentIndex > 0) currentIndex--
                        } else {
                            if (currentIndex < mediaList.size - 1) currentIndex++
                        }
                        change.consume()
                    }
                }
        ) {
            MediaCard(
                estateMedia = mediaList[currentIndex],
                isSuppressButtonEnable = isSuppressButtonEnable,
                onSuppressClick = onSuppressClick,
                modifier = Modifier.align(Alignment.Center)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        if (currentIndex > 0) currentIndex--
                    },
                    enabled = currentIndex > 0
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                }

                IconButton(
                    onClick = {
                        if (currentIndex < mediaList.size - 1) currentIndex++
                    },
                    enabled = currentIndex < mediaList.size - 1
                ) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Next")
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PictureCard(imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink), modifier = modifier)
        }
    }
}

@Preview
@Composable
fun MediaCardPreview(){
    AppTheme {
        Surface(modifier = Modifier.height(670.dp).width(360.dp)) {
            MediaCard(filePath = "modern_bathroom.webp",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
        }
    }
}

@Preview
@Composable
fun MediaCardListPreview(){
    AppTheme {
        Surface(modifier = Modifier.height(670.dp).width(360.dp)) {
            MediaCardList(
                mediaList = listOf(),
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
        }
    }
}