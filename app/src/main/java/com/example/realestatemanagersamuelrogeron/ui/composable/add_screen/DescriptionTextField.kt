package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DescriptionTextField(uText: String = "",what: String, icon: ImageVector) {
    var text by remember {
        mutableStateOf(uText)
    }
    OutlinedTextField(modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = { newText ->
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
        singleLine = false,
        maxLines = 5
    )
}