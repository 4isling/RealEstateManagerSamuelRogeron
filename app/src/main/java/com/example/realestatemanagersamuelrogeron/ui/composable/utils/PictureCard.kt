package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun PictureCard(
    uri: String,
    modifier: Modifier,
) {

    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation()

    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun PictureCard(
    uri: Uri,
    modifier: Modifier,
    isSuppressButtonEnable: Boolean = false,
    onSuppressClick: (Uri) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        modifier = modifier,
    ) {
        if (isSuppressButtonEnable) {
            Icon(modifier = Modifier.clickable {
                onSuppressClick(uri)
            }, imageVector = RemIcon.Remove, contentDescription = null)
        }
        AsyncImage(
            model = uri,
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
        AsyncImage(
            model = imageVector,
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