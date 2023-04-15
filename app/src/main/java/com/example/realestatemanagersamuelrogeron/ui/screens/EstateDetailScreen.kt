package com.example.realestatemanagersamuelrogeron.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.size.Dimension
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel


@Composable
fun EstateDetailScreen(navController: NavController, estateDetailViewModel: EstateDetailViewModel) {
    var estate = estateDetailViewModel.getEstateDetail().observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = estate.value?.title.toString())
                },
                navigationIcon = {

                },
                actions = {

                }
            )
        },
        content = { contentPadding ->
            LazyColumn(contentPadding = PaddingValues(12.dp)

            ){

            }
        })
}