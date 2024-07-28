package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
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
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints


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
        windowSizeClass = windowSizeClass
    )
}

@Composable
fun EstateDetailScreenContent(
    uiState: DetailViewState,
    onBackPress: () -> Unit,
    onEdit: () -> Unit = {},
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
    onEdit: () -> Unit = {},
    isEuro: Boolean
) {
    val isEditable = remember {
        mutableListOf(false)
    }
    // Your UI implementation to display estate details
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            EstateDetailPhoneScreen(
                estateWithDetail,
                onBackPress,
                onEdit,
                isEuro
            )
        }

        WindowWidthSizeClass.Medium -> {
            // UI for small tablets
            Surface(modifier = Modifier.fillMaxSize()) {
                Row {
                    Column {
                        Text(text = estateWithDetail.estate.title)
                        // More UI elements...
                    }
                    // Additional UI elements for larger screen
                }
            }

        }

        WindowWidthSizeClass.Expanded -> {
            // UI for large tablets
            EstateDetailTabletScreen(
                estateWithDetail,
                onEdit,
                isEuro,
            )
        }
    }
}

@Composable
fun EstateDetailPhoneScreen(
    estateWithDetail: EstateWithDetails,
    onBackPress: () -> Unit = {},
    onEdit: () -> Unit = {},
    isEuro: Boolean
) {
    Scaffold(
        topBar = {
            RemDetailTopAppBar(
                title = estateWithDetail.estate.title,
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
                        mediaList = estateWithDetail.estatePictures,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                }
                item {
                    PriceText(
                        estatePrice = estateWithDetail.estate.price,
                        modifier = Modifier.padding(4.dp),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        toEuro = isEuro
                    )
                }
                item {
                    InterestPointsBox(
                        isEditable = false,
                        interestPoints = estateWithDetail.estateInterestPoints,
                    )
                }
                item {
                    LocationBox(
                        address = estateWithDetail.estate.address,
                        city = estateWithDetail.estate.city,
                        region = estateWithDetail.estate.region,
                        country = estateWithDetail.estate.country,
                        modifier = Modifier.padding(4.dp),
                        staticMap = "TODO" //@TODO
                    )
                }
                item {
                    DescriptionBox(
                        description = estateWithDetail.estate.description,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    )
                }
            }
        })

}

@Composable
fun EstateDetailTabletScreen(
    estateWithDetails: EstateWithDetails,
    onEdit: () -> Unit,
    isEuro: Boolean,
) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentSize()
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        RoundedCornerShape(8.dp)
                    ),
                elevation = CardDefaults.cardElevation(8.dp), // Set the desired elevation value here
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = estateWithDetails.estate.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(16.dp)
            )
            {
                Icon(imageVector = RemIcon.Edit, contentDescription = "Edit Button")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .padding(16.dp)
        ) {
            MediaCardList(
                mediaList = estateWithDetails.estatePictures,
                modifier = Modifier.fillMaxSize(),
                isSuppressButtonEnable = false,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .padding(16.dp)
        ) {
            LocationBox(
                address = estateWithDetails.estate.address,
                city = estateWithDetails.estate.city,
                region = estateWithDetails.estate.region,
                country = estateWithDetails.estate.country,
                modifier = Modifier.padding(4.dp),
                staticMap = "TODO" //@TODO
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
fun DetailPhoneScreenPreview() {
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(360.dp, 640.dp) // Example dimensions for a phone screen
    )
    AppTheme {
        Surface(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .size(1824.dp, 1600.dp)
        ) {
            EstateDetailScreenContent(
                windowSizeClass = windowSizeClass,
                uiState = DetailViewState.Success(
                    estate = EstateWithDetails(
                        Estate(
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
                        estatePictures = listOf(EstateMedia(0, 0, "HURI", "", "")),
                        estateInterestPoints = predefinedInterestPoints,
                    ),
                    isEuro = false
                ),
                onBackPress = {},
                onEdit = {}
            )
        }
    }
}

@Preview
@Composable
fun DetailTabletScreenPreview() {
    AppTheme {
        EstateDetailTabletScreen(
            estateWithDetails = EstateWithDetails(
                Estate(
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
                estatePictures = listOf(EstateMedia(0, 0, "HURI", "", "")),
                estateInterestPoints = predefinedInterestPoints,
            ),
            onEdit = { /*TODO*/ },
            isEuro = true
        )
    }
}


