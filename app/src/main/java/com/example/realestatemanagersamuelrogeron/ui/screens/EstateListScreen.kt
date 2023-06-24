package com.example.realestatemanagersamuelrogeron.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.data.event.ListScreenEvent
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.state.EstateState
import com.example.realestatemanagersamuelrogeron.data.state.ListViewState
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.EstateItem
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.SortMenu
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel
import kotlin.reflect.KFunction1


@Composable
fun EstateListScreen(
    navController: NavController,
    viewModel: EstatesListViewModel = hiltViewModel()
) {
    val viewState by viewModel.viewState.collectAsState()
    var sortMenuExpend by remember {
        mutableStateOf(false)
    }
    val sortOptions = listOf(
        SortType.Default,
        SortType.PriceDescend,
        SortType.PriceGrow,
        SortType.RentDescend,
        SortType.RentGrow
    )
    var selectedFilter by remember {
        mutableStateOf(sortOptions)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "RealEstateManager")
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Drawer Icon")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search Estate"
                        )
                    }
                    SortMenu(
                        sortOptions,
                        onSortOptionSelected = {
                            viewModel.onSortTypeValueChange(it)
                        }
                    )
                    IconButton(onClick = {
                        navController.navigate(Screen.AddEstate.route)
                    }) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "addEstate")
                    }
                }

            )
        },
        content =  { innerPadding ->
            when (viewState) {
                is ListViewState.Loading -> {
                    Text(text = "loading")
                }

                is ListViewState.Success -> EstateList(
                    innerPadding,
                    (viewState as ListViewState.Success).estates,
                    navController,
                    viewModel
                )

                is ListViewState.Error -> Text(text = (viewState as ListViewState.Error).exception)
            }
        }
    )
}

@Composable
fun EstateList(
    contentPadding: PaddingValues,
    estates: List<Estate>,
    navController: NavController,
    viewModel: EstatesListViewModel
) {
    val lazyListState = rememberLazyListState()
    val canLoadMoreItems by viewModel.canLoadMoreItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        state = lazyListState,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (estates.isNotEmpty()) {
            items(estates) { estate ->
                EstateItem(entry = estate, navController = navController)
            }
        } else {
            item {
                Text(
                    text = "Nothing to show",
                    fontSize = MaterialTheme.typography.h4.fontSize
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EstateListScreenPreview() {
}


@Composable
fun TopAppBarCompose() {
    val context = LocalContext.current

}