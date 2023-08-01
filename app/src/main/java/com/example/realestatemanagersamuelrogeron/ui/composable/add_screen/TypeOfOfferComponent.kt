package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.AddEstateViewModel

@Composable
fun TypeOfOfferComponent(viewModel: AddEstateViewModel) {
    Row(){
        Button(onClick = {

        }) {
            Text(
                text = "Rent",
            )
        }
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = "Sell",
            )
        }
    }
}