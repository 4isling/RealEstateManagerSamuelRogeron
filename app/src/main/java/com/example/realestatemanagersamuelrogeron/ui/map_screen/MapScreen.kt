package com.example.realestatemanagersamuelrogeron.ui.map_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.EstateItem
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.RemBottomAppBar
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.calculateWindowSizeClass
import com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel.MapState
import com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel.MapViewModel
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberMarkerState

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    val TAG = "MapScreen: "
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    val windowSizeClass = calculateWindowSizeClass()

    MapScreenContent(
        windowSizeClass = windowSizeClass,
        viewState = uiState,
        onClickList = {
            navController.navigate(Screen.EstateList.route)
        },
        onClickSetting = {
            //@TODO
        },
        onClickAddEstate = {
            navController.navigate(Screen.AddEstate.route)
        },
        onEstateMakerClick = {
            viewModel.updateSelectedEstate(it)
        },
        onEstateItemClick = {
            navController.navigate(Screen.EstateDetail.route)
        },
        onFilterChange = {
            viewModel.updateFilter(newFilter = it)
        },
    )
}

@Composable
fun MapScreenContent(
    windowSizeClass: WindowSizeClass,
    viewState: MapState,
    onClickList: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
    onClickAddEstate: () -> Unit = {},
    onEstateMakerClick: (Long) -> Unit = {},
    onEstateItemClick: (Long) -> Unit = {},
    onFilterChange: (EstateFilter) -> Unit = {}
) {
    when (viewState) {
        is MapState.Loading -> {
            CircularProgressIndicator()
        }

        is MapState.Success -> {
            windowSizeClass.widthSizeClass
            var showEstateItem by remember {
                mutableStateOf(viewState.estateSelected != null)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                bottomEnd = 10.dp,
                                bottomStart = 10.dp
                            )
                        ),
                ) {
                    val context = LocalContext.current
                    if (showEstateItem) {
                        viewState.estateSelected?.let {
                            EstateItem(
                                entry = it,
                                onEstateItemClick = onEstateItemClick,
                                modifier = Modifier.wrapContentSize()
                            )
                        }
                    }
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        uiSettings = MapUiSettings(zoomControlsEnabled = false),
                        properties = MapProperties(
                            // mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json)
                        ),
                        cameraPositionState = CameraPositionState(
                            CameraPosition(
                                LatLng(22.5726, 88.3639), 12f, 0f, 0f
                            )
                        ),
                    ) {
                        viewState.estates.forEach {
                            val id = it.estate.estateId
                            val markerState = rememberMarkerState(
                                position = LatLng(
                                    it.estate.lat!!,
                                    it.estate.lng!!
                                )
                            )
                            Marker(
                                state = markerState,
                                title = it.estate.title,
                                snippet = it.estate.price.toString() +
                                        if (it.estate.typeOfOffer == "Rent") {
                                            " $/m"
                                        } else {
                                            " $"
                                        },
                                onClick = {
                                    onEstateMakerClick(id)
                                    false
                                }
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .rotate(180f)
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.DarkGray
                                    ),
                                    startY = 10f
                                ),
                            )
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color(0x6F202020)
                                    ),
                                ),
                            ),
                        contentAlignment = Alignment.BottomCenter,
                    ) {
                        RemBottomAppBar(
                            selectedScreen = "Map",
                            onScreenSelected = onClickList,
                            onClickSetting = onClickSetting,
                            onClickAdd = onClickAddEstate,
                        )
                    }
                }
            }
        }

        is MapState.Error -> {
            val state = viewState as MapState.Error
            // Show error message
            Text(text = "Error: ${state.exception}")
        }
    }
}

