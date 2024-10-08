package com.example.realestatemanagersamuelrogeron.ui.add_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.CameraHandlerTablet
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.InterestPointsPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.SelectTextField
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointItem
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCard
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AddEstateTabletScreen(
    onBackPress: () -> Unit = {},
    onSavePress: () -> Unit = {},
    uiState: AddEstateState,
    onFieldChange: (String, String) -> Unit,
    onMediaSelected: (List<Uri>) -> Unit = {},
    onImageCaptured: (Uri) -> Unit = {},
    onMediaRemoved: (Uri) -> Unit = {},
    onInterestPointsSelected: (List<EstateInterestPoints>) -> Unit = {},
    onInterestPointCreated: (String, Int) -> Unit,
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit = {}
) {
    val openMediaDialog = remember { mutableStateOf(false) }
    var openCameraHandler by remember { mutableStateOf(false) }
    val openInterestPointDialog = remember { mutableStateOf(false) }
    val openMediaPicker = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            TopAppBar(
                title = { Text("Add New Estate") },
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Button(onClick = onSavePress) {
                        Text(text = "Save")
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.title,
                        onValueChange = { onFieldChange("title", it) },
                        label = { Text("Title") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = uiState.titleError
                    )

                    // Media selection
                    if (uiState.newMediaUris.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.newMediaUris) { uri ->
                                MediaCard(
                                    filePath = uri.toString(),
                                    modifier = Modifier.size(150.dp),
                                    isSuppressButtonEnable = true,
                                    onSuppressClick = { onMediaRemoved(uri) }
                                )
                            }
                        }
                    }

                    Button(
                        onClick = { openMediaDialog.value = true },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Select Pictures")
                    }

                    SelectTextField(
                        label = "Type",
                        selectedOption = uiState.estateWithDetails.estate.typeOfEstate,
                        onOptionSelected = { onFieldChange("type", it) },
                        options = listOf("Apartment", "House", "Garage", "Land-field", "Warehouse")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.estateWithDetails.estate.etage,
                            onValueChange = { onFieldChange("etage", it) },
                            label = { Text("Etage") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = uiState.etagesError
                        )
                        OutlinedTextField(
                            value = uiState.estateWithDetails.estate.nbRooms.toString(),
                            onValueChange = { onFieldChange("nbRooms", it) },
                            label = { Text("Number of Rooms") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,

                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            isError = uiState.nbRoomsError
                        )
                    }

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.surface.toString(),
                        onValueChange = { onFieldChange("surface", it) },
                        label = { Text("Surface") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.surfaceError
                    )
                }

                // Right column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SelectTextField(
                        label = "Offer",
                        selectedOption = uiState.estateWithDetails.estate.typeOfOffer,
                        onOptionSelected = { onFieldChange("offer", it) },
                        options = listOf("Rent", "Sell")
                    )

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.price.toString() ,
                        onValueChange = { onFieldChange("price", it) },
                        label = { Text("Price") },
                        suffix = {
                            if (uiState.isEuro) {
                                Text(text = "€")
                            }else {
                                Text(text = "$")
                            }

                        },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.priceError,
                        singleLine = true,

                    )

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.address,
                        onValueChange = { onFieldChange("address", it) },
                        label = { Text("Address") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.addressError,
                        singleLine = true,

                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = uiState.estateWithDetails.estate.zipCode,
                            onValueChange = { onFieldChange("zipCode", it) },
                            label = { Text("Zip Code") },
                            modifier = Modifier.weight(1f),
                            isError = uiState.zipCodeError,
                            singleLine = true,

                        )
                        OutlinedTextField(
                            value = uiState.estateWithDetails.estate.city,
                            onValueChange = { onFieldChange("city", it) },
                            label = { Text("City") },
                            modifier = Modifier.weight(1f),
                            isError = uiState.cityError,
                            singleLine = true,

                        )
                    }

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.region,
                        onValueChange = { onFieldChange("region", it) },
                        label = { Text("Region") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.regionError,
                        singleLine = true,

                    )

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.country,
                        onValueChange = { onFieldChange("country", it) },
                        label = { Text("Country") },
                        modifier = Modifier.fillMaxWidth(),
                        isError = uiState.countryError,
                        singleLine = true,

                    )

                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.description,
                        onValueChange = { onFieldChange("description", it) },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        isError = uiState.descriptionError
                    )

                    // Interest Points
                    Text("Interest Points", style = MaterialTheme.typography.titleMedium)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        uiState.estateWithDetails.estateInterestPoints.forEach { interestPoint ->
                            InterestPointItem(
                                interestPoints = interestPoint,
                                editEnable = false,
                                onClickRemove = { onInterestPointItemRemove(interestPoint) }
                            )
                        }
                    }

                    Button(
                        onClick = { openInterestPointDialog.value = true },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Add Interest Points")
                    }
                }
            }
        }

        // Dialogs
        if (openMediaDialog.value) {
            MediaPickerDialog(
                onClickCamera = {
                    openCameraHandler = true
                    openMediaDialog.value = false
                },
                onClickAlbum = {
                    openMediaPicker.value = true
                    openMediaDialog.value = false
                },
                onClickCancel = {
                    openMediaDialog.value = false
                }
            )
        }

        if (openCameraHandler) {
            CameraHandlerTablet(
                onImageCaptured = { uri ->
                    if (uri != null) {
                        onImageCaptured(uri)
                    }
                    openCameraHandler = false
                },
                onCloseCamera = {
                    openCameraHandler = false
                }
            )
        }
        if (openMediaPicker.value) {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickMultipleVisualMedia(),
                onResult = { uris ->
                    onMediaSelected(uris)
                    openMediaPicker.value = false
                }
            )
            LaunchedEffect(Unit) {
                launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            }
        }

        if (openInterestPointDialog.value) {
            InterestPointsPickerDialog(
                interestPoints = uiState.allInterestPoints,
                initiallySelectedPoints = uiState.estateWithDetails.estateInterestPoints,
                onDismiss = { openInterestPointDialog.value = false },
                onCreateInterestPoint = onInterestPointCreated,
                onPointsSelected = onInterestPointsSelected
            )
        }
    }
}

@Composable
fun InterestPointChip(
    interestPoint: EstateInterestPoints,
    onRemove: () -> Unit
) {
    Surface(
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = interestPoint.interestPointsName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Remove",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun AddEstateTabletScreenPreview() {
    AppTheme {
        AddEstateTabletScreen(
            onBackPress = {},
            onSavePress = {},
            uiState = AddEstateState(),
            onFieldChange = { _, _ -> },
            onMediaSelected = { _ ->  },
            onImageCaptured = {},
            onMediaRemoved = {},
            onInterestPointItemRemove = {},
            onInterestPointsSelected = {},
            onInterestPointCreated = { _, _ -> }
        )
    }
}