package com.example.realestatemanagersamuelrogeron.ui.add_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.CameraHandler
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.InterestPointsPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.SelectTextField
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointItem
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCard

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddEstatePhoneScreen(
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Estate") },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.testTag("add_estate_phone_screen_back_button"),
                        onClick = onBackPress
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag("add_estate_phone_screen_save_button"),
                        onClick = onSavePress
                    ) {
                        Icon(Icons.Default.Check, contentDescription = "Save")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.title,
                    onValueChange = { onFieldChange("title", it) },
                    label = { Text("Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_title_text_field"),
                    isError = uiState.titleError
                )
            }

            item {
                // Media selection
                if (uiState.newMediaUris.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.newMediaUris) { uri ->
                            MediaCard(
                                filePath = uri.toString(),
                                modifier = Modifier.size(100.dp),
                                isSuppressButtonEnable = true,
                                onSuppressClick = { onMediaRemoved(uri) }
                            )
                        }
                    }
                }

                Button(
                    onClick = { openMediaDialog.value = true },
                    modifier = Modifier.fillMaxWidth().testTag("add_estate_phone_screen_open_media_picker_button")
                ) {
                    Text("Select Pictures")
                }
            }

            item {
                SelectTextField(
                    label = "Type",
                    selectedOption = uiState.estateWithDetails.estate.typeOfEstate,
                    onOptionSelected = { onFieldChange("type", it) },
                    options = listOf("Apartment", "House", "Garage", "Land-field", "Warehouse")
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.etage,
                        onValueChange = { onFieldChange("etage", it) },
                        label = { Text("Etage") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_estate_phone_screen_etage_text_field"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.etagesError
                    )
                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.nbRooms.toString(),
                        onValueChange = { onFieldChange("nbRooms", it) },
                        label = { Text("Rooms") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_estate_phone_screen_nbrooms_text_field"),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        isError = uiState.nbRoomsError
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.surface.toString(),
                    onValueChange = { onFieldChange("surface", it) },
                    label = { Text("Surface") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_surface_text_field"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.surfaceError
                )
            }

            item {
                SelectTextField(
                    label = "Offer",
                    selectedOption = uiState.estateWithDetails.estate.typeOfOffer,
                    onOptionSelected = { onFieldChange("offer", it) },
                    options = listOf("Rent", "Sell")
                )
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.price.toString(),
                    onValueChange = { onFieldChange("price", it) },
                    label = { Text("Price") },
                    suffix = {
                        if (uiState.isEuro) {
                            Text(text = "€")
                        }else {
                            Text(text = "$")
                        }

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_price_text_field"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.priceError
                )
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.address,
                    onValueChange = { onFieldChange("address", it) },
                    label = { Text("Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_address_text_field"),
                    isError = uiState.addressError
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.zipCode,
                        onValueChange = { onFieldChange("zipCode", it) },
                        label = { Text("Zip Code") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_estate_phone_screen_zipcode_text_field"),
                        isError = uiState.zipCodeError
                    )
                    OutlinedTextField(
                        value = uiState.estateWithDetails.estate.city,
                        onValueChange = { onFieldChange("city", it) },
                        label = { Text("City") },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("add_estate_phone_screen_city_text_field"),
                        isError = uiState.cityError
                    )
                }
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.region,
                    onValueChange = { onFieldChange("region", it) },
                    label = { Text("Region") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_region_text_field"),
                    isError = uiState.regionError
                )
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.country,
                    onValueChange = { onFieldChange("country", it) },
                    label = { Text("Country") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_country_text_field"),
                    isError = uiState.countryError
                )
            }

            item {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.description,
                    onValueChange = { onFieldChange("description", it) },
                    label = { Text("Description") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_description_text_field"),
                    minLines = 3,
                    isError = uiState.descriptionError
                )
            }

            item {
                Text("Interest Points", style = MaterialTheme.typography.titleMedium)
                FlowRow(
                    modifier = Modifier.fillMaxWidth().testTag("add_estate_phone_screen_interest_points_flow_row"),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.estateWithDetails.estateInterestPoints.forEach { interestPoint ->
                        InterestPointItem(
                            interestPoints = interestPoint,
                            editEnable = true,
                            onClickRemove = { onInterestPointItemRemove(interestPoint) }
                        )
                    }
                }
                Button(
                    onClick = { openInterestPointDialog.value = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("add_estate_phone_screen_open_interest_point_dialog_button")
                ) {
                    Text("Add Interest Points")
                }
            }
        }
    }

    // Dialogs and pickers
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
        CameraHandler(onImageCaptured = { uri ->
            if (uri != null) {
                onImageCaptured(uri)
            }
            openCameraHandler = false
        })
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