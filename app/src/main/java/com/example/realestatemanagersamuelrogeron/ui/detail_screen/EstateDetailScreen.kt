package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.MediaCardList
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.PriceText
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemDetailTopAppBar
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.DescriptionBox
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.InterestPointsBox
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.composable.LocationBox
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.DetailViewState
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.EstateDetailViewModel
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints


@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateDetailScreen(
    navController: NavController,
    viewModel: EstateDetailViewModel = hiltViewModel()

) {
    val windowSizeClass = calculateWindowSizeClass()
    val uiState by viewModel.uiState.collectAsState()

    EstateDetailScreenContent(
        uiState = uiState,
        windowSizeClass = windowSizeClass,
        onBackPress = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact) {
                navController.navigateUp()
            }
        }
    )
}

@Composable
fun EstateDetailScreenContent(
    uiState: DetailViewState,
    windowSizeClass: WindowSizeClass,
    onBackPress: () -> Unit,
    onEdit: () -> Unit = {},
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
                windowSizeClass = windowSizeClass,
                estate = state.estate,
                medias = state.medias,
                interestPoints = state.interestPoints,
                onBackPress = onBackPress
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
    estate: Estate,
    medias: List<EstateMedia>,
    interestPoints: List<EstateInterestPoints>,
    onBackPress: () -> Unit = {},
    onEdit: () -> Unit = {},
) {
    val isEditable = remember {
        mutableListOf(false)
    }
    // Your UI implementation to display estate details
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            Scaffold(
                topBar = {
                    RemDetailTopAppBar(
                        title = estate.title,
                        modifier = Modifier,
                        onBackPress = {
                            onBackPress()
                        },
                        onEditIconClick = {
                            onEdit()
                        },
                    )
                },
                content = { padding ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues = padding)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            MediaCardList(
                                mediaList = medias,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(4.dp)
                            )
                        }
                        item {
                            PriceText(
                                estatePrice = estate.price,
                                modifier = Modifier.padding(4.dp),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        item {
                            InterestPointsBox(
                                isEditable = false,
                                interestPoints = interestPoints,
                            )
                        }
                        item {
                            LocationBox(
                                address = estate.address,
                                city = estate.city,
                                region = estate.region,
                                country = estate.country,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        item {
                            DescriptionBox(
                                description = estate.description,
                                modifier = Modifier.padding(4.dp).fillMaxWidth()
                            )
                        }
                    }
                })
        }

        WindowWidthSizeClass.Medium -> {
            // UI for small tablets
            Surface(modifier = Modifier.fillMaxSize()) {
                Row {
                    Column {
                        Text(text = estate.title)
                        // More UI elements...
                    }
                    // Additional UI elements for larger screen
                }
            }

        }

        WindowWidthSizeClass.Expanded -> {
            // UI for large tablets
            Row {
                Column {
                    Text(text = estate.title)
                    // More UI elements...
                }
                // Additional UI elements for even larger screen
            }
        }
    }
}

// Helper function to calculate WindowSizeClass
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun calculateWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val windowMetrics = LocalContext.current.getSystemService(WindowManager::class.java)
        .currentWindowMetrics

    val widthDp = windowMetrics.bounds.width() / configuration.densityDpi.toFloat()
    val heightDp = windowMetrics.bounds.height() / configuration.densityDpi.toFloat()

    return remember(configuration) {
        WindowSizeClass.calculateFromSize(DpSize(widthDp.dp, heightDp.dp))
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun DetailScreenPreview() {
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(360.dp, 640.dp) // Example dimensions for a phone screen
    )
    AppTheme {
        EstateDetailScreenContent(
            windowSizeClass = windowSizeClass,
            uiState = DetailViewState.Success(
                estate = Estate(
                    estateId = 0,
                    title = "La forge D'Entre Mont",
                    typeOfOffer = "Rent",
                    typeOfEstate = "House",
                    etage = "2",
                    address = "address",
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
                medias = listOf(EstateMedia(0, 0, "HURI", "")),
                interestPoints = predefinedInterestPoints
            ),
            onBackPress = {},
            onEdit = {}
        )
    }
}


