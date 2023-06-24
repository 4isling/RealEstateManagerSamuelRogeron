package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCard
import com.example.realestatemanagersamuelrogeron.ui.theme.Shapes
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel
import java.io.IOException
import androidx.compose.material.Icon as Icon

@Composable
fun AddListOfPictures(viewModel: AddEstateViewModel) {
    val picker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris ->
                viewModel.onPicturesValueChange(uris)
                Log.d("AddEstateScreen", "Media selected: $uris")
            }
        )
    var showDialog by remember {
        mutableStateOf(false)
    }
    val pics by viewModel.estatePictures.collectAsState()

    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painterResource(id = R.drawable.baseline_add_a_photo_24),
            contentDescription = "imagesAdd",
            modifier =
            Modifier
                .border(BorderStroke(width = 2.dp, color = Color.Black), Shapes.medium)
                .clickable(
                    true,
                    onClick = {
                        showDialog = true
                        picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                )
        )
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(pics) { uri ->
                PictureCard(uri = uri)
            }
        }
    }
}