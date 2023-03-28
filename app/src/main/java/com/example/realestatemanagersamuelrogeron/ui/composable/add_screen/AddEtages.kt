package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AddEtages(what: String, icon: ImageVector){
    var text by remember {
        mutableStateOf(what)
    }
    TextField(value = text, onValueChange = {
            newText ->
        text = newText
    },
        label = {
            Text(text = what)
        },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = icon, contentDescription = "Icon of $what")
            }
        },
        singleLine = true,

        )
}