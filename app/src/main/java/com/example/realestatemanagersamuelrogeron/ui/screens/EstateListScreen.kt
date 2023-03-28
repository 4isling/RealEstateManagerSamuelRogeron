package com.example.realestatemanagersamuelrogeron.ui.screens

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel


@Composable
fun EstateListScreen(navController: NavController, estatesListViewModel: EstatesListViewModel){

    val result = estatesListViewModel.getAllEstates().observeAsState(listOf())


    if(result.value.isNotEmpty()) {
/*    LazyColumn(
        contentPadding = PaddingValues(all =12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ){*/
/*
            itemsIndexed(items = result) { index, item ->
                Log.d("EstateList", index.toString())
                EstateItem(entry = item, navController = navController)
            }
*/        }
    //   } else {
    Text(
        text = "Nothing to show",
        fontSize = MaterialTheme.typography.h4.fontSize
    )
    //   }

}

@Preview(showBackground = true)
@Composable
fun EstateListScreenPreview() {
}


@Composable
fun TopAppBarCompose(){
    val context = LocalContext.current

}