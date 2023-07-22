package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.ui.navigation.Screen
import com.example.realestatemanagersamuelrogeron.ui.screens.MapScreen
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel
import net.bytebuddy.pool.TypePool.Default.LazyTypeDescription.GenericTypeToken.Resolution.Raw

@Composable
fun DrawerListContent(
    viewModel: EstatesListViewModel,
    navController: NavController
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (Modifier.clickable{
            navController.navigate(Screen.Map.route)
        }){
            Icon(painter = painterResource(id = R.drawable.baseline_map_24), contentDescription = "Map icon")
            Text(text = "Map")
        }

        Row {
           Icon(imageVector = Icons.Default.Settings, contentDescription = "settings Icon")
            Text(text = "Setting")
        }

    }
}