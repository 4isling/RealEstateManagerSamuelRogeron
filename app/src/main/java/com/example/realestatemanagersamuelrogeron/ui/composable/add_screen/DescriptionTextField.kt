package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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