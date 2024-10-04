package com.example.realestatemanagersamuelrogeron.ui.edit_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCardList
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.viewmodel.EditEstateState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.ui.theme.remTextFieldColors
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEstatePhoneScreen(
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onMediaSelected: (List<Uri>) -> Unit = {},
    onFieldChange: (String, String) -> Unit,
    onInterestPointsSelected: (List<EstateInterestPoints>) -> Unit = {},
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit = {},
    onInterestPointCreated: (String, Int) -> Unit,
    onMediaRemoved: (Uri) -> Unit = {},
    uiState: EditEstateState,
) {
    val openMediaDialog = remember { mutableStateOf(false) }
    var openCameraHandler by remember { mutableStateOf(false) }
    val openInterestPointDialog = remember { mutableStateOf(false) }
    val openMediaPicker = remember { mutableStateOf(false) }
    val focusRequesters = remember { List(11) { FocusRequester() } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Estate") },
                navigationIcon = {
                    IconButton(onClick = onSave) {
                        Icon(imageVector = RemIcon.Save, contentDescription = "Save")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onDelete
                    }) {
                        Icon(imageVector = RemIcon.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues = padding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.title,
                    onValueChange = { onFieldChange("title", it) },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                MediaCardList(
                    mediaList = uiState.estateWithDetails.estatePictures,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    isSuppressButtonEnable = true,
                    onSuppressClick = onMediaRemoved,
                )
                Button(
                    onClick = {
                        openMediaDialog.value = true
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(36.dp)
                ) {
                    Text(text = "Select Pictures")
                }
            }
            item {
                OutlinedTextField(
                    colors = remTextFieldColors(),
                    value = uiState.displayPrice,
                    isError = uiState.priceError,
                    onValueChange = { newValue -> onFieldChange("price", newValue) },
                    label = { Text(text = "Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                InterestPointsBox(
                    modifier = Modifier,
                    editEnable = true,
                    interestPointsList = uiState.estateWithDetails.estateInterestPoints,
                    onClickRemove = onInterestPointItemRemove
                )
            }
            item {
                Button(
                    onClick = {
                        openInterestPointDialog.value = true
                    },
                    modifier = Modifier.wrapContentSize()

                ) {
                    Text(text = "Select Interest Points")
                }
            }
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.address,
                    onValueChange = { onFieldChange("address", it) },
                    label = { Text("Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.city,
                    onValueChange = { onFieldChange("city", it) },
                    label = { Text("City") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.region,
                    onValueChange = { onFieldChange("region", it) },
                    label = { Text("Region") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.country,
                    onValueChange = { onFieldChange("country", it) },
                    label = { Text("Country") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.description,
                    onValueChange = { onFieldChange("description", it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    minLines = 3
                )
            }
        }

        val picker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickMultipleVisualMedia(),
            onResult = { uris: List<Uri> ->
                if (uris.isNotEmpty()) {
                    onMediaSelected(uris)
                }
            }
        )
        if (openMediaDialog.value) {
            MediaPickerDialog(
                onClickCamera = {
                    openCameraHandler = true
                    openMediaDialog.value = false
                },
                onClickAlbum = {
                    picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                    openMediaDialog.value = false
                },
                onClickCancel = {
                    openMediaDialog.value = false
                },
            )
        }

    }
}

@Preview
@Composable
fun EditPhoneScreenPreview() {
    AppTheme {
        EditEstatePhoneScreen(
            onSave = { /*TODO*/ },
            onDelete = { /*TODO*/ },
            onFieldChange = { _, _ -> },
            uiState = EditEstateState(
                estateWithDetails = EstateWithDetails(
                    estate = Estate(
                        estateId = 0,
                        title = "La forge D'Entre Mont",
                        typeOfOffer = "Rent",
                        typeOfEstate = "House",
                        etage = "2",
                        address = "Address",
                        zipCode = "",
                        city = "Pierre-Feu",
                        description = "oé c'est une description",
                        addDate = 0,
                        sellDate = 0,
                        agent = "",
                        price = 2500,
                        surface = 200,
                        nbRooms = 4,
                        status = true,
                    ),
                    estatePictures = listOf(EstateMedia(0, 0, "HURI", "", "")),
                    estateInterestPoints = predefinedInterestPoints,
                ),
            ),
            onInterestPointCreated = { _, _ -> },
        )
    }
}