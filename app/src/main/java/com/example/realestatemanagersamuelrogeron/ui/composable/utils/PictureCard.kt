package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import okhttp3.HttpUrl

@Composable
fun PictureCard(
    uri: Uri,
    modifier: Modifier = Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit = {}
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
        )

        if (isSuppressButtonEnable) {
            IconButton(
                onClick = { onSuppressClick(uri) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove image",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun PictureCard(
    url: HttpUrl,
    modifier: Modifier,
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = modifier,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PictureCard(
    imageVector: ImageVector,
    modifier: Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = modifier,
    ) {
        Image(
            imageVector = imageVector,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun PictureCardPreview() {
    val context = LocalContext.current
    val uri = Uri.parse("res/drawable/moderncubic_house.webp")

    PictureCard(
        uri = uri,
        modifier = Modifier
            .height(128.dp)
            .width(104.dp),
        onSuppressClick = {}
    )
}