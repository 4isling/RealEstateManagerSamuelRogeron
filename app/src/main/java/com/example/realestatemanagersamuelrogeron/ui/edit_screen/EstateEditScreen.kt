package com.example.realestatemanagersamuelrogeron.ui.edit_screen

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.viewmodel.EditEstateState
import com.example.realestatemanagersamuelrogeron.ui.edit_screen.viewmodel.EditEstateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateEditScreen(
    navController: NavController,
    viewModel: EditEstateViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isEstateSaved){
        navController.navigateUp()
    }

    EstateEditScreenContent(
        uiState = uiState,
        onBackPress = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                navController.navigateUp()
            }
        },
        onFieldChange = viewModel::updateEstateProperty,
        onMediaSelected = viewModel::onMediaPicked,
        onImageCaptured = viewModel::onImageCaptured,
        onMediaRemoved = viewModel::onMediaRemoved,
        onInterestPointsSelected = viewModel::onInterestPointSelected,
        onInterestPointCreated = viewModel::onCreateNewInterestPoint,
        onInterestPointItemRemove = viewModel::removeSelectedInterestPoint,
        onSave = { viewModel.onSaveButtonClick() },
        windowSizeClass = windowSizeClass,
        onDelete = viewModel::onDeleteEstate
    )
}

@Composable
fun EstateEditScreenContent(
    uiState: EditEstateState,
    onBackPress: () -> Unit,
    onSave: () -> Unit,
    onFieldChange: (String, String) -> Unit,
    onMediaSelected: (List<Uri>) -> Unit = {},
    onImageCaptured: (Uri) -> Unit = {},
    onMediaRemoved: (Uri) -> Unit = {},
    onInterestPointsSelected: (List<EstateInterestPoints>) -> Unit = {},
    onInterestPointCreated: (String, Int) -> Unit,
    onInterestPointItemRemove: (EstateInterestPoints) -> Unit = {},
    windowSizeClass: WindowSizeClass,
    onDelete: () -> Unit = {}
){
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            EditEstateTabletScreen(
                onSave = onSave,
                onDelete = onDelete,
                onMediaSelected = onMediaSelected,
                onFieldChange = onFieldChange,
                onImageCaptured = onImageCaptured,
                onInterestPointsSelected = onInterestPointsSelected,
                onInterestPointItemRemove = onInterestPointItemRemove,
                onInterestPointCreated = onInterestPointCreated,
                onMediaRemoved = onMediaRemoved,
                uiState = uiState,
            )
        }
        else -> {
            EditEstatePhoneScreen(
                onSave = onSave,
                onDelete = onDelete,
                onMediaSelected = onMediaSelected,
                onImageCaptured = onImageCaptured,
                onFieldChange = onFieldChange,
                onInterestPointsSelected = onInterestPointsSelected,
                onInterestPointItemRemove = onInterestPointItemRemove,
                onInterestPointCreated = onInterestPointCreated,
                onMediaRemoved = onMediaRemoved,
                uiState = uiState,
            )
        }
    }
}