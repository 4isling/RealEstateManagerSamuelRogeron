package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun AddTextFieldNoIcon(uText: String = "", what: String, modifier: Modifier) {
    var text by remember {
        mutableStateOf(uText)
    }
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = {
            Text(text = what)
        },

        singleLine = true,
        modifier = modifier
        )
}