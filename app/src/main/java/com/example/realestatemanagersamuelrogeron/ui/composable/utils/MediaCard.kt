package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
fun MediaCard(
    filePath: String,
    modifier: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {}
) {
    val uri = Uri.parse(filePath)
    val context = LocalContext.current
    val mimeType = FileTypeHelper.getMimeType(context = context, uri = uri)
    when {
        mimeType?.startsWith("image") == true -> PictureCard(
            uri = uri,
            modifier = modifier,
            isSuppressButtonEnable = isSuppressButtonEnable,
            onSuppressClick = onSuppressClick
        )

        mimeType?.startsWith("video") == true -> VideoCard(
            videoUri = uri,
            modifier = modifier,
            isSuppressButtonEnable = isSuppressButtonEnable,
            onSuppressClick = onSuppressClick
        )

        else -> Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
            modifier = modifier,
            contentDescription = null
        )

    }
}

@Composable
fun MediaCard(
    estateMedia: EstateMedia,
    modifier: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {}
) {
    when {
        estateMedia.mimeType?.startsWith("image") == true -> PictureCard(
            uri = estateMedia.uri.toUri(),
            modifier = modifier,
            isSuppressButtonEnable = isSuppressButtonEnable,
            onSuppressClick = onSuppressClick
        )

        estateMedia.mimeType?.startsWith("video") == true -> VideoCard(
            videoUri = estateMedia.uri.toUri(),
            modifier = modifier,
            isSuppressButtonEnable = isSuppressButtonEnable,
            onSuppressClick = onSuppressClick
        )

        else -> Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
            modifier = modifier,
            contentDescription = null
        )

    }
}

@Composable
fun MediaCard(
    filePath: String,
    modifierImage: Modifier = Modifier,
    modifierVideo: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {}
) {

    val uri = Uri.parse(filePath)
    val context = LocalContext.current
    val mimeType = FileTypeHelper.getMimeType(context = context, uri = uri)
    when {
        mimeType?.startsWith("image") == true ->
            PictureCard(
                uri = uri,
                modifier = modifierImage,
                isSuppressButtonEnable = isSuppressButtonEnable,
                onSuppressClick = onSuppressClick
            )

        mimeType?.startsWith("video") == true ->
            VideoCard(
                videoUri = uri,
                modifier = modifierVideo,
                isSuppressButtonEnable = isSuppressButtonEnable,
                onSuppressClick = onSuppressClick
            )

        else -> Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
            modifier = modifierImage,
            contentDescription = null
        )
    }
}

@Composable
fun MediaCardList(
    mediaList: List<EstateMedia>,
    modifier: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {},
) {
    if (mediaList.isNotEmpty()) {
        Card(
            modifier = modifier
                .wrapContentSize()
                .background(color = MaterialTheme.colorScheme.inversePrimary),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(mediaList) { media ->
                    MediaCard(
                        estateMedia = media,
                        isSuppressButtonEnable = isSuppressButtonEnable,
                        onSuppressClick = onSuppressClick,
                        modifier = Modifier.size(200.dp) // Adjust size as needed
                    )
                }
            }
        }
    } else {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            PictureCard(
                imageVector = ImageVector.vectorResource(id = R.drawable.no_media_ink),
                modifier = modifier
            )
        }
    }
}

@Preview
@Composable
fun MediaCardPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
        ) {
            MediaCard(
                filePath = "modern_bathroom.webp",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
        }
    }
}

@Preview
@Composable
fun MediaCardListPreview() {
    AppTheme {
        Surface(
            modifier = Modifier
                .height(670.dp)
                .width(360.dp)
        ) {
            MediaCardList(
                mediaList = listOf(),
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                isSuppressButtonEnable = false,
                onSuppressClick = {}
            )
        }
    }
}