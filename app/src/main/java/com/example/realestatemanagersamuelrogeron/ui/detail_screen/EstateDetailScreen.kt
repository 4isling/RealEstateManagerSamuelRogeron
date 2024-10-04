package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.DetailViewState
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateDetailScreen(
    navController: NavController,
    viewModel: EstateDetailViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
) {
    val uiState by viewModel.uiState.collectAsState()

    EstateDetailScreenContent(
        uiState = uiState,
        onBackPress = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                navController.navigateUp()
            }
        },
        windowSizeClass = windowSizeClass,
        onEdit = {
            navController.safeNavigate("${Screen.Edit.route}/${it}")
        }

    )
}

@Composable
fun EstateDetailScreenContent(
    uiState: DetailViewState,
    onBackPress: () -> Unit,
    onEdit: (Long) -> Unit = {},
    windowSizeClass: WindowSizeClass,
    ) {
    when (uiState) {
        is DetailViewState.Loading -> {
            // Show loading indicator
            CircularProgressIndicator()
        }

        is DetailViewState.Success -> {
            val state = uiState as DetailViewState.Success
            // Show estate details
            EstateDetailContent(
                onEdit = onEdit,
                windowSizeClass = windowSizeClass,
                estateWithDetail = state.estate,
                onBackPress = onBackPress,
                isEuro = state.isEuro,
            )
        }

        is DetailViewState.Error -> {
            val state = uiState as DetailViewState.Error
            // Show error message
            Text(text = "Error: ${state.exception}")
        }
    }
}

@Composable
fun EstateDetailContent(
    windowSizeClass: WindowSizeClass,
    estateWithDetail: EstateWithDetails,
    onBackPress: () -> Unit = {},
    onEdit: (Long) -> Unit = {},
    isEuro: Boolean
) {
    val isEditable = remember {
        mutableListOf(false)
    }
    // Your UI implementation to display estate details
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            // UI for large tablets
            EstateDetailTabletScreen(
                estateWithDetail,
                onEdit,
                isEuro,
            )
        }
        else -> {
            EstateDetailPhoneScreen(
                estateWithDetail,
                onBackPress,
                onEdit,
                isEuro
            )
        }
    }
}








