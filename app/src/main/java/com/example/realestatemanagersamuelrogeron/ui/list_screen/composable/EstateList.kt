package com.example.realestatemanagersamuelrogeron.ui.list_screen.composable

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel.EstatesListViewModel

@Composable
fun EstateList(
    contentPadding: PaddingValues,
    navController: NavController,
    viewModel: EstatesListViewModel,
) {
    val estatesWithPictures by viewModel.estatesWithPictures.collectAsState(emptyList())
    val lazyListState = rememberLazyListState()
    val canLoadMoreItems by viewModel.canLoadMoreItems.collectAsState()
    //val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { /*TODO*/ })

    if (estatesWithPictures.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(estatesWithPictures) { estateWithPic ->
                Log.i(
                    ContentValues.TAG,
                    "EstateList: EstateWithPic = ${estateWithPic.estate}+ ${estateWithPic.pictures.size}"
                )
                EstateItem(
                    entry = estateWithPic.estate,
                    navController = navController,
                    pic = estateWithPic.pictures
                )
            }
        }
    } else {
        Text(
            text = "Nothing to show",
        )
    }
}

@Composable
fun EstateList(
    contentPadding: PaddingValues,
    estateWithPictureList: List<EstateWithPictures>,
    onEstateItemClick: (Long) -> Unit,
){
    val lazyListState = rememberLazyListState()

    if (estateWithPictureList.isNotEmpty()) {
        LazyColumn(
            state = lazyListState,
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(estateWithPictureList) { estateWithPic ->
                Log.i(
                    ContentValues.TAG,
                    "EstateList: EstateWithPic = ${estateWithPic.estate}+ ${estateWithPic.pictures.size}"
                )
                EstateItem(
                    entry = estateWithPic.estate,
                    onEstateItemClick = onEstateItemClick,
                    pic = estateWithPic.pictures
                )
            }
        }
    } else {
        Text(
            text = "Nothing to show",
        )
    }
}