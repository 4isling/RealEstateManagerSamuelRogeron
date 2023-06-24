package com.example.realestatemanagersamuelrogeron.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.size.Dimension
import com.example.realestatemanagersamuelrogeron.data.event.DetailScreenEvent
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.state.EstateState
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateDetailViewModel


@Composable
fun EstateDetailScreen(navController: NavController,viewModel: EstateDetailViewModel = hiltViewModel()) {

    val estate = viewModel.state
    estate.let {
        val  context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "${it.value.title}")
                    },
                    navigationIcon = {

                    },
                    actions = {

                    }
                )
            },
            content = { contentPadding ->
                EstateDetail(
                    estate= it.value,
                    modifier = Modifier.padding(contentPadding))
            })

    }
}

@Composable
fun EstateDetail(estate: Estate, modifier: Modifier){
    Column(modifier = modifier.fillMaxSize()) {

    }
}