package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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