package com.example.realestatemanagersamuelrogeron.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.ui.composable.list_screen.EstateItem
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel


@Composable
fun EstateListScreen(navController: NavController, estatesListViewModel: EstatesListViewModel){
    val result = estatesListViewModel.getAllEstates().observeAsState(listOf())

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
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "Search Estate")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.baseline_filter_alt_24), contentDescription = "Filter Icons")
                    }
                    IconButton(onClick = {
                        navController.navigate(Screen.AddEstate.route)
                    }) {
                        Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "addEstate")
                    }
                }
            )
        }
    ){
        contentPadding ->
        if(result.value.isNotEmpty()) {
            LazyColumn(
                contentPadding = PaddingValues(all =12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ){
                items(result.value){
                        estate-> EstateItem(entry = estate, navController = navController, viewModel = estatesListViewModel)
                }
            }
        } else {
            Text(
                text = "Nothing to show",
                fontSize = MaterialTheme.typography.h4.fontSize
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun EstateListScreenPreview() {
}


@Composable
fun TopAppBarCompose(){
    val context = LocalContext.current

}