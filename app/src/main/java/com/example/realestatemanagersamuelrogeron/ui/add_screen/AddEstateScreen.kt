package com.example.realestatemanagersamuelrogeron.ui.add_screen

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateState
import com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel.AddEstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.calculateWindowSizeClass
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEstateScreen(
    viewModel: AddEstateViewModel = hiltViewModel(),
    navController: NavController,
    windowSizeClass: WindowSizeClass,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusRequesters = List(10) { FocusRequester() }

    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    AddEstateScreen(
        onBackPress = { navController.navigateUp() },
        onSavePress = viewModel::onSaveButtonClick,
        uiState = uiState,
        onFieldChange = viewModel::onFieldChange,
        onMediaSelected = viewModel::onMediaPicked,
        onImageCaptured = viewModel::onImageCaptured,
        onMediaRemoved = viewModel::onMediaRemoved,
        onInterestPointsSelected = viewModel::onInterestPointSelected,
        onInterestPointCreated = viewModel::onCreateNewInterestPoint,
        onInterestPointItemRemove = viewModel::removeSelectedInterestPoint,
        windowSizeClass = windowSizeClass,
        )

    LaunchedEffect(uiState.isEstateSaved) {
        if (uiState.isEstateSaved) {
            navController.navigateUp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEstateScreen(
    onBackPress: () -> Unit = {},
    onSavePress: () -> Unit = {},
    uiState: AddEstateState,
    windowSizeClass: WindowSizeClass,
    onFieldChange: (String, String) -> Unit,
    onMediaSelected: (List<Uri>) -> Unit = {},
    onImageCaptured: (Uri) -> Unit = {},
    onMediaRemoved: (Uri) -> Unit = {},
    onInterestPointsSelected: (List<EstateInterestPoints>) -> Unit = {},
    onInterestPointCreated: (String, Int) -> Unit,
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit = {}
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            AddEstatePhoneScreen(
                onBackPress = onBackPress,
                onSavePress = onSavePress,
                uiState = uiState,
                onFieldChange = onFieldChange,
                onMediaSelected = onMediaSelected,
                onImageCaptured = onImageCaptured,
                onMediaRemoved = onMediaRemoved,
                onInterestPointsSelected = onInterestPointsSelected,
                onInterestPointItemRemove = onInterestPointItemRemove,
                onInterestPointCreated = onInterestPointCreated,
            )
        }

        WindowWidthSizeClass.Medium -> {
            AddEstatePhoneScreen(
                onBackPress = onBackPress,
                onSavePress = onSavePress,
                uiState = uiState,
                onFieldChange = onFieldChange,
                onMediaSelected = onMediaSelected,
                onImageCaptured = onImageCaptured,
                onMediaRemoved = onMediaRemoved,
                onInterestPointsSelected = onInterestPointsSelected,
                onInterestPointItemRemove = onInterestPointItemRemove,
                onInterestPointCreated = onInterestPointCreated,
            )
        }

        WindowWidthSizeClass.Expanded -> {
            AddEstateTabletScreen(
                onBackPress = onBackPress,
                onSavePress = onSavePress,
                uiState = uiState,
                onFieldChange = onFieldChange,
                onMediaSelected = onMediaSelected,
                onImageCaptured = onImageCaptured,
                onMediaRemoved = onMediaRemoved,
                onInterestPointsSelected = onInterestPointsSelected,
                onInterestPointItemRemove = onInterestPointItemRemove,
                onInterestPointCreated = onInterestPointCreated,
            )
        }
    }
}


@ExperimentalMaterial3WindowSizeClassApi
@Preview
@Composable
fun AddEstateScreenPreview() {
    val mockUiState = AddEstateState(
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
    )
    AppTheme {
        AddEstateScreen(
            uiState = mockUiState,
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(360.dp, 640.dp)),
            onFieldChange = { _, _ -> /* Handle field changes */ },
            onSavePress = { /* Handle save */ },
            onBackPress = { /* Handle back navigation */ },
            onInterestPointCreated = { _, _ -> },
            onMediaRemoved = {},
            onImageCaptured = {},
            onInterestPointsSelected = {},
            onMediaSelected = {},
        )
    }

}

@Preview
@Composable
fun AddEstateScreenDarkPreview() {
    val mockUiState = AddEstateState(
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
    )
    AppTheme(darkTheme = true) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            AddEstateScreen(
                uiState = mockUiState,
                windowSizeClass = calculateWindowSizeClass(),
                onFieldChange = { _, _ -> /* Handle field changes */ },
                onSavePress = { /* Handle save */ },
                onBackPress = { /* Handle back navigation */ },
                onInterestPointCreated = { _, _ -> },
                onMediaRemoved = {},
                onImageCaptured = {},
                onInterestPointsSelected = {},
                onMediaSelected = {},
            )
        }
    }

}