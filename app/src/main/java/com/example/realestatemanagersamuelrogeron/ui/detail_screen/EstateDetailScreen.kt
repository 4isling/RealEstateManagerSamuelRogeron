package com.example.realestatemanagersamuelrogeron.ui.detail_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.ui.composable.detail_screen.EstateDetail
import com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel.EstateDetailViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstateDetailScreen(
    navController: NavController,
    viewModel: EstateDetailViewModel = hiltViewModel()
) {
    val estate by viewModel.estate.collectAsState()
    val detailState by viewModel.uiState.collectAsStateWithLifecycle()

    estate.let {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = it.title)
                    },
                    navigationIcon = {

                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = "Modify estate",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            if (!it.isFav) {
                                Icon(
                                    imageVector = Icons.Rounded.FavoriteBorder,
                                    contentDescription = "not Fav",
                                    tint = Color.White
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Rounded.Favorite,
                                    contentDescription = "Fav",
                                    tint = Color.Yellow
                                )
                            }
                        }
                    }
                )
            },
        ) { contentPadding ->
            EstateDetail(
                detailState = detailState,
                modifier = Modifier.padding(contentPadding)
            )
        }
    }

}

