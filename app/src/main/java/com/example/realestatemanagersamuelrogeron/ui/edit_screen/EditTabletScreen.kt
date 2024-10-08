package com.example.realestatemanagersamuelrogeron.ui.edit_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.CameraHandlerTablet
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.InterestPointsPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.MediaPickerDialog
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCardList
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.viewmodel.EditEstateState
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditEstateTabletScreen(
    onSave: () -> Unit,
    onDelete: () -> Unit,
    onFieldChange: (String, String) -> Unit,
    onMediaRemoved: (Uri) -> Unit = {},
    onMediaSelected: (List<Uri>) -> Unit = {},
    onImageCaptured: (Uri) -> Unit = {},
    onInterestPointsSelected: (List<EstateInterestPoints>) -> Unit = {},
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit = {},
    onInterestPointCreated: (String, Int) -> Unit,
    uiState: EditEstateState
) {
    val openMediaDialog = remember { mutableStateOf(false) }
    var openCameraHandler by remember { mutableStateOf(false) }
    val openInterestPointDialog = remember { mutableStateOf(false) }
    val openMediaPicker = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title and Action Buttons
            TitleAndActionButtons(uiState, onFieldChange, onSave, onDelete)
            Spacer(modifier = Modifier.height(16.dp))

            // Main content area
            Row(modifier = Modifier.weight(1f)) {
                // Left column: Images, Price, Surface, Description
                Column(modifier = Modifier.weight(0.6f)) {
                    MediaSection(uiState, onMediaRemoved, openMediaDialog)
                    Spacer(modifier = Modifier.height(16.dp))
                    PriceAndSurfaceSection(uiState, onFieldChange)
                    Spacer(modifier = Modifier.height(16.dp))
                    DescriptionSection(uiState, onFieldChange)
                }
                Spacer(modifier = Modifier.width(16.dp))
                // Right column: Location and Interest Points
                Column(modifier = Modifier.weight(0.4f)) {
                    LocationSection(uiState, onFieldChange)
                    Spacer(modifier = Modifier.height(16.dp))
                    InterestPointsSection(uiState, onInterestPointItemRemove, openInterestPointDialog)
                }
            }
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
            },
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

@Composable
private fun TitleAndActionButtons(
    uiState: EditEstateState,
    onFieldChange: (String, String) -> Unit,
    onSave: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = uiState.estateWithDetails.estate.title,
            onValueChange = { onFieldChange("title", it) },
            label = { Text("Title") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        FloatingActionButton(
            onClick = onSave,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Icon(imageVector = RemIcon.Save, contentDescription = "Save")
        }
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onDelete,
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ) {
            Icon(imageVector = RemIcon.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
private fun MediaSection(
    uiState: EditEstateState,
    onMediaRemoved: (Uri) -> Unit,
    openMediaDialog: MutableState<Boolean>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            MediaCardList(
                mediaList = uiState.estateWithDetails.estatePictures,
                modifier = Modifier.weight(1f),
                isSuppressButtonEnable = true,
                onSuppressClick = onMediaRemoved
            )
            Button(
                onClick = { openMediaDialog.value = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Text("Select Pictures")
            }
        }
    }
}

@Composable
private fun PriceAndSurfaceSection(uiState: EditEstateState, onFieldChange: (String, String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = uiState.estateWithDetails.estate.surface.toString(),
            onValueChange = { onFieldChange("surface", it) },
            label = { Text("Surface (m²)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DescriptionSection(uiState: EditEstateState, onFieldChange: (String, String) -> Unit) {
    OutlinedTextField(
        value = uiState.estateWithDetails.estate.description,
        onValueChange = { onFieldChange("description", it) },
        label = { Text("Description") },
        modifier = Modifier.fillMaxWidth(),
        minLines = 4
    )
}

@Composable
private fun LocationSection(uiState: EditEstateState, onFieldChange: (String, String) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Location", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.estateWithDetails.estate.address,
                onValueChange = { onFieldChange("address", it) },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.city,
                    onValueChange = { onFieldChange("city", it) },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = uiState.estateWithDetails.estate.zipCode,
                    onValueChange = { onFieldChange("zipCode", it) },
                    label = { Text("Zip Code") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.estateWithDetails.estate.region,
                onValueChange = { onFieldChange("region", it) },
                label = { Text("Region") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.estateWithDetails.estate.country,
                onValueChange = { onFieldChange("country", it) },
                label = { Text("Country") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun InterestPointsSection(
    uiState: EditEstateState,
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit,
    openInterestPointDialog: MutableState<Boolean>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Interest Points", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            InterestPointsBox(
                modifier = Modifier.fillMaxWidth(),
                interestPointsList = uiState.estateWithDetails.estateInterestPoints,
                editEnable = true,
                onClickRemove = onInterestPointItemRemove
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { openInterestPointDialog.value = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Select Interest Points")
            }
        }
    }
}

@Preview(widthDp = 1280, heightDp = 800)
@Composable
fun EditTabletScreenPreview() {
    AppTheme {
        EditEstateTabletScreen(
            onSave = {  },
            onDelete = {  },
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
                    estatePictures = emptyList(),
                    estateInterestPoints = predefinedInterestPoints,
                )
            ),
            onInterestPointCreated = { _, _ -> },
        )
    }
}