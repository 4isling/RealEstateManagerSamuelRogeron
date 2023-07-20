package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PictureSwitcher(pictures: List<EstatePictures>) {
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
                model = estatePicture.pictureUri.toUri(),
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