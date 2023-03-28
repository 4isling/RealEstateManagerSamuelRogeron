package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*

@Composable
fun AddTextFieldNoIcon(what: String) {
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

        singleLine = true,

        )
}