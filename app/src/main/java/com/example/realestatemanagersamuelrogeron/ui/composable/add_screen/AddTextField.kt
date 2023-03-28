package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


/**
 * TextField
 * @what: String for what should be in label and empty textField
 * @icon: is the image-vector that will be display on the left of the text-field
 */
@Composable
fun AddTextField(what: String, icon: ImageVector){
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview(){
        AddTextField(what = "test", icon = Icons.Default.Email )
}