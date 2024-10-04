package com.example.realestatemanagersamuelrogeron.ui.list_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.composable.utils.safeNavigate
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.EstatesListViewModel
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen

@Composable
fun EstateListScreen(
    viewModel: EstatesListViewModel = hiltViewModel(),
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    onEstateSelected: (Long) -> Unit = {}
) {
    val TAG = "EstateListScreen:"
    var sortMenuExpend by remember {
        mutableStateOf(false)
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val drawerItems = listOf(
        "Map",
    )

    val uiState by viewModel.viewState.collectAsStateWithLifecycle()
    EstateListScreen(

        estateListState = uiState,
        onEstateItemClick = {
            navController.safeNavigate("${Screen.EstateDetail.route}/${it}")
        },
        onAddEstateClick = {
            navController.navigate(Screen.AddEstate.route)
        },
        onFilterChange = {
            viewModel.updateFilter(newFilter = it)
        },
        onClickMap = {
            navController.navigate(Screen.Map.route)
        },
        onClickSetting = {
            navController.navigate(Screen.Settings.route)
        },
        windowSizeClass = windowSizeClass,
    )
}

@Composable
fun EstateListScreen(
    estateListState: ListViewState,
    onEstateItemClick: (Long) -> Unit,
    onAddEstateClick: () -> Unit = {},
    onFilterChange: (EstateFilter) -> Unit = {},
    onClickMap: (String) -> Unit = {},
    onClickSetting: () -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Expanded -> {  //tablet landscape
            EstateListTabletScreen(
                modifier = Modifier,
                estateListState = estateListState,
                onEstateItemClick = onEstateItemClick,
                onFilterChange = onFilterChange,
            )
        }
        else -> {
            EstateListPhoneScreen(
                modifier = Modifier.padding(
                    start = 0.dp,
                    top = 0.dp,
                    end = 0.dp,
                    bottom = 56.dp
                ),
                estateListState = estateListState,
                onEstateItemClick = onEstateItemClick,
                onAddEstateClick = onAddEstateClick,
                onFilterChange = onFilterChange,
                onClickMap = onClickMap,
                onClickSetting = onClickSetting,
            )
        }
    }

}


@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current

}