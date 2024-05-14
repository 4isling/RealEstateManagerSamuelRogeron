package com.example.realestatemanagersamuelrogeron.ui.add_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.CameraHandler
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.InterestPointsPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPicker
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.SelectTextField
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCard
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.ui.theme.remTextFieldColors
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun AddEstateScreen(
    viewModel: AddEstateViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AddEstateScreen(
        onBackPress = { navController.navigateUp() },
        onSavePress = { viewModel::saveEstate },
        uiState = uiState,
        onFieldChange = viewModel::onFieldChange,
        onMediaSelected = viewModel::onMediaPicked,
        onImageCaptured = viewModel::onImageCaptured,
        onMediaRemoved = viewModel::onMediaRemoved,
        onInterestPointsSelected = viewModel::onInterestPointSelected,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstateScreen(
    onBackPress: () -> Unit = {},
    onSavePress: () -> Unit = {},
    uiState: AddEstateState,
    onFieldChange: (String, String) -> Unit,
    onMediaSelected: (List<Uri>) -> Unit = {},
    onImageCaptured: (Uri) -> Unit = {},
    onMediaRemoved: (Uri) -> Unit = {},
    onInterestPointsSelected: (List<String>) -> Unit = {},
) {
    val openMediaDialog = remember { mutableStateOf(false) }
    val openCameraHandler = remember { mutableStateOf(false) }
    val openInterestPointDialog = remember { mutableStateOf(false) }
    val openMediaPicker = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    if (openCameraHandler.value) {
                        Text(text = "Camera")
                    } else {
                        Text(text = "Create Estate")
                    }

                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!openCameraHandler.value) {
                                onBackPress()
                            } else {
                                openCameraHandler.value = false
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.clickable { onBackPress() })
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    colors = remTextFieldColors(),
                    value = uiState.title,
                    onValueChange = { newValue -> onFieldChange("title", newValue) },
                    label = { Text(text = "Title") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                )

                // Picture
                if (uiState.mediaSelected.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(36.dp)
                            .border(
                                width = 1.dp,
                                color = if (uiState.mediaError) {
                                    MaterialTheme.colorScheme.onErrorContainer
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Column {
                            LazyRow {
                                val pics = uiState.mediaSelected
                                items(pics) { uri ->
                                    MediaCard(
                                        filePath = uri.toString(),
                                        modifierImage = Modifier
                                            .height(124.dp)
                                            .width(82.dp),
                                        modifierVideo = Modifier
                                            .height(82.dp)
                                            .width(124.dp),
                                        isSuppressButtonEnable = true,
                                        onSuppressClick = onMediaRemoved,
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    openMediaDialog.value = true
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(36.dp)
                            ) {
                                Text(text = "Select Pictures")
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            openMediaDialog.value = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(36.dp)
                    ) {
                        Text(text = "Select Pictures")
                    }
                }
                // Select Type
                SelectTextField(
                    label = "Type",
                    selectedOption = uiState.type,
                    onOptionSelected = { newValue -> onFieldChange("type", newValue) },
                    options = listOf("Apartment", "House", "Garage", "Land-field", "Warehouse")
                )
                // Detail
                Box(modifier = Modifier) {
                    Column {
                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            value = uiState.etages,
                            isError = uiState.etagesError,
                            onValueChange = { newValue -> onFieldChange("etage", newValue) },
                            label = { Text(text = "Etage") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            keyboardActions = KeyboardActions(),
                        )
                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            value = uiState.nbRooms,
                            isError = uiState.nbRoomsError,
                            onValueChange = { newValue -> onFieldChange("nbRooms", newValue) },
                            label = { Text(text = "Number of Rooms") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        )
                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            isError = uiState.surfaceError,
                            value = uiState.surface.toString(),
                            onValueChange = { newValue -> onFieldChange("surface", newValue) },
                            label = { Text(text = "Surface") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                        )
                    }
                }

                // Select Offer
                SelectTextField(
                    label = "Offer",
                    selectedOption = uiState.offer,
                    onOptionSelected = { newValue -> onFieldChange("offer", newValue) },
                    options = listOf("Rent", "Sell")
                )

                // Price Box
                Box {
                    if (uiState.offer == "Sell") {
                        OutlinedTextField(
                            value = uiState.sellingPrice.toString(),
                            isError = uiState.sellingPriceError,
                            onValueChange = { newValue -> onFieldChange("sellingPrice", newValue) },
                            label = { Text(text = "Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            colors = remTextFieldColors()
                        )
                    }
                    if (uiState.offer == "Rent") {
                        OutlinedTextField(
                            value = uiState.rent.toString(),
                            onValueChange = { newValue -> onFieldChange("rent", newValue) },
                            label = { Text(text = "Price") },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            singleLine = true,
                            isError = uiState.rentError,
                        )
                    }
                }

                // Location Box
                Box {
                    Column {
                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            value = uiState.address,
                            onValueChange = { newValue -> onFieldChange("address", newValue) },
                            label = { Text(text = "Address") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default,
                            keyboardActions = KeyboardActions.Default,
                            isError = uiState.addressError
                        )

                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            value = uiState.zipCode,
                            onValueChange = { newValue -> onFieldChange("zipCode", newValue) },
                            label = {
                                Text(text = "Zip Code")
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default,
                            keyboardActions = KeyboardActions.Default,
                            isError = uiState.zipCodeError,
                        )

                        OutlinedTextField(
                            colors = remTextFieldColors(),
                            value = uiState.city,
                            onValueChange = { newValue -> onFieldChange("city", newValue) },
                            label = { Text(text = "City") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default,
                            keyboardActions = KeyboardActions.Default,
                            isError = uiState.cityError,
                        )
                    }
                }

                OutlinedTextField(
                    colors = remTextFieldColors(),
                    value = uiState.description,
                    onValueChange = { newValue -> onFieldChange("description", newValue) },
                    label = { Text(text = "Description") },
                    keyboardOptions = KeyboardOptions.Default,
                    keyboardActions = KeyboardActions.Default,
                    isError = uiState.descriptionError,
                )

                if (uiState.interestPointsStrings.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .padding(36.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Column {
                            LazyRow(content = {
                                items(uiState.interestPointsStrings) { point ->
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(5.dp))
                                            .background(Color.Gray)
                                    ) {
                                        Text(text = point)
                                        IconButton(onClick = {
                                            //remove interest point
                                        }) {
                                            Icon(
                                                imageVector = RemIcon.Remove,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }
                            })
                            Button(
                                onClick = {
                                    openInterestPointDialog.value = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Select Interest Points")
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = {
                            openInterestPointDialog.value = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Select Interest Points")
                    }
                }

                // for each interest point a tag with interest point created
                Button(
                    onClick = { onSavePress },
                    enabled = true
                ) {
                    Text(text = "Create Estate")
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
                        openCameraHandler.value = true
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
            if (openInterestPointDialog.value) {
                InterestPointsPickerDialog()
            }
            if (openCameraHandler.value) {

                CameraHandler(onImageCaptured = { uri ->
                    if (uri != null) {
                        onImageCaptured(uri)
                    }
                })
            }
            if (openMediaPicker.value) {
                MediaPicker(onMediaSelected = onMediaSelected)
            }
        }
    )
}

@Preview
@Composable
fun AddEstateScreenPreview() {
    val mockUiState = AddEstateState(
        title = "Sample Title",
        mediaSelected = listOf(),
        type = "House",
        etages = "2",
        nbRooms = "4",
        surface = "120",
        offer = "Sell",
        sellingPrice = "300000",
        address = "123 Main St",
        zipCode = "90210",
        city = "Beverly Hills",
        description = "A beautiful spacious house."
    )

    AppTheme {
        AddEstateScreen(
            uiState = mockUiState,
            onFieldChange = { _, _ -> /* Handle field changes */ },
            onSavePress = { /* Handle save */ },
            onBackPress = { /* Handle back navigation */ }
        )
    }

}

@Preview
@Composable
fun AddEstateScreenDarkPreview() {
    val mockUiState = AddEstateState(
        title = "Sample Title",
        mediaSelected = listOf(),
        type = "House",
        etages = "2",
        nbRooms = "4",
        surface = "120",
        offer = "Sell",
        sellingPrice = "300000",
        address = "123 Main St",
        zipCode = "90210",
        city = "Beverly Hills",
        description = "A beautiful spacious house."
    )

    AppTheme(useDarkTheme = true) {
        AddEstateScreen(
            uiState = mockUiState,
            onFieldChange = { _, _ -> /* Handle field changes */ },
            onSavePress = { /* Handle save */ },
            onBackPress = { /* Handle back navigation */ }
        )
    }

}