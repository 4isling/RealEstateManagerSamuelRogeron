package com.example.realestatemanagersamuelrogeron.ui.shared.list_screen.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

@Composable
fun PictureSwitcher(pictures: List<EstateMedia>) {
    var selectedIndex by remember { mutableStateOf(0) }
    var offsetX by remember { mutableStateOf(0f) }

    val size = LocalConfiguration.current.screenWidthDp.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, _, _ ->
                    offsetX += pan.x
                }
            }
    ) {
        pictures.forEachIndexed { index, estatePicture ->
            val pictureOffset = (index - selectedIndex) * size + offsetX.dp

            AsyncImage(
                model = estatePicture.uri.toUri(),
                contentDescription ="",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .offset(x = pictureOffset)
                    .clipToBounds()
                    .clickable {
                        selectedIndex = index
                    }
                )
        }
    }
}