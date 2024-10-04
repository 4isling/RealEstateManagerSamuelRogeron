package com.example.realestatemanagersamuelrogeron.ui.map_screen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel.MapState
import com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel.MapViewModel
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@Composable
fun MapScreen(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass,
    onEstateSelected:(Long) -> Unit = {}
) {
    val TAG = "MapScreen: "
    val uiState by viewModel.viewState.collectAsStateWithLifecycle()

    MapScreenContent(
        viewState = uiState,
        windowSizeClass = windowSizeClass,
        onClickList = {
            navController.navigate(Screen.EstateList.route)
        },
        onClickSetting = {
            navController.navigate(Screen.Settings.route)
        },
        onClickAddEstate = {
            navController.navigate(Screen.AddEstate.route)
        },
        onEstateMakerClick = {
            viewModel.updateSelectedEstate(it)
        },
        onEstateItemClick = {
            navController.safeNavigate("${Screen.EstateDetail.route}/${it}")
        },
        onFilterChange = {
            viewModel.updateFilter(newFilter = it)
        },
        onClickMap = {
            viewModel.unselectEstate()
        }
    )
}

@Composable
fun MapScreenContent(
    viewState: MapState,
    windowSizeClass: WindowSizeClass,
    onClickList: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
    onClickAddEstate: () -> Unit = {},
    onEstateMakerClick: (Long) -> Unit = {},
    onEstateItemClick: (Long) -> Unit = {},
    onFilterChange: (EstateFilter) -> Unit = {},
    onClickMap: () -> Unit = {},
) {
    when (viewState) {
        is MapState.Loading -> {
            CircularProgressIndicator()
        }

        is MapState.Success -> {
            when (windowSizeClass.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    MapPhoneScreen(
                        estates = viewState.estates,
                        isEuro = viewState.isEuro,
                        estateSelected = viewState.estateSelected,
                        onClickList = onClickList,
                        onClickSetting = onClickSetting,
                        onClickAddEstate = onClickAddEstate,
                        onEstateMakerClick = onEstateMakerClick,
                        onEstateItemClick = onEstateItemClick,
                        onFilterChange = onFilterChange,
                        onClickMap = onClickMap,
                    )
                }

                WindowWidthSizeClass.Medium -> {

                }

                WindowWidthSizeClass.Expanded -> {
                    MapTabletScreen(
                        estates = viewState.estates,
                        isEuro = viewState.isEuro,
                        estateSelected = viewState.estateSelected,
                        onEstateItemClick = onEstateItemClick,
                        onClickMap = onClickMap,
                        onEstateMakerClick = onEstateMakerClick,
                    )
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

fun setCustomMapIcon(line1: String, line2: String): BitmapDescriptor {
    val height = 200f // Adjusted height to accommodate two lines
    val widthPadding = 80.dp.value
    val lineHeight = 50f // Height for each line
    val maxWidth =
        maxOf(paintTextWhite.measureText(line1), paintTextWhite.measureText(line2)) + widthPadding
    val roundStart = height / 4
    val path = Path().apply {
        // Start of the first arc on the left side
        arcTo(
            0f,
            0f,
            roundStart * 2,
            height * 2 / 3,
            -90f,
            -180f,
            true
        )

        // Draw line to the middle bottom left of the pointer part
        lineTo(maxWidth / 2 - roundStart / 2, height * 2 / 3)

        // Draw line to the tip of the pointer part
        lineTo(maxWidth / 2, height)

        // Draw line to the middle bottom right of the pointer part
        lineTo(maxWidth / 2 + roundStart / 2, height * 2 / 3)

        // Draw line to the start of the second arc on the right side
        lineTo(maxWidth - roundStart, height * 2 / 3)

        // Start of the second arc on the right side
        arcTo(
            maxWidth - roundStart * 2,
            0f, maxWidth,
            height * 2 / 3,
            90f,
            -180f,
            true
        )
        // Draw line back to the start point of the first arc
        lineTo(roundStart, 0f)
    }

    val bm = Bitmap.createBitmap(maxWidth.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bm)
    canvas.drawPath(path, paintBlackFill)
    canvas.drawPath(path, paintWhite)

    // Draw the first line of text
    canvas.drawText(line1, maxWidth / 2, lineHeight, paintTextWhite)
    // Draw the second line of text below the first line
    canvas.drawText(line2, maxWidth / 2, lineHeight * 2, paintTextWhite)

    return BitmapDescriptorFactory.fromBitmap(bm)
}

val paintBlackFill = Paint().apply {
    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
    isAntiAlias = true
    color = Color.DarkGray.hashCode()
    style = Paint.Style.FILL
    textAlign = Paint.Align.CENTER
    textSize = 30.dp.value
}

val paintTextWhite = Paint().apply {
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
    isAntiAlias = true
    color = Color.White.hashCode()
    textAlign = Paint.Align.CENTER
    strokeWidth = 6.dp.value
    textSize = 48.dp.value
}

val paintWhite = Paint().apply {
    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
    strokeJoin = Paint.Join.ROUND
    isAntiAlias = true
    color = Color.White.hashCode()
    strokeWidth = 6.dp.value
}
