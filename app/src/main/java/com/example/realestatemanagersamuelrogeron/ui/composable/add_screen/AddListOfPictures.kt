package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.PictureCard
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel

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
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                Image(
                    painterResource(id = R.drawable.baseline_add_a_photo_24),
                    contentDescription = "imagesAdd",
                    modifier =
                    Modifier
                        .border(
                            BorderStroke(width = 2.dp, color = Color.Black),
                            shape = CircleShape
                        )
                        .clickable(
                            true,
                            onClick = {
                                showDialog = true
                                picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                            }
                        )
                )
            }
            items(pics) { uri ->
                PictureCard(uri = uri, modifier = Modifier.padding(1.dp))
            }
        }
    }
}