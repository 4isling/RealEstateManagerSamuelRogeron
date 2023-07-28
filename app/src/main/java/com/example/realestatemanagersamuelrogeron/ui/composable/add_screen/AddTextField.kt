package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


/**
 * TextField
 * @what: String for what should be in label and empty textField
 * @icon: is the image-vector that will be display on the left of the text-field
 */
@Composable
fun AddTextField(uText: String = "",what: String, icon: ImageVector,modifier: Modifier) {
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
        leadingIcon = {

                Icon(imageVector = icon, contentDescription = "Icon of $what")

        },
        singleLine = true,

    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Surface(Modifier.fillMaxSize()) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically ) {
            AddTextField(what = "place", icon = Icons.Default.LocationOn, modifier = Modifier.fillMaxWidth(0.7f))
            AddTextField(what = "test", icon = Icons.Default.Email, modifier = Modifier.fillMaxWidth(0.3f))
        }

    }

}