package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AddNumberTextField(modifier: Modifier, what: String, uText: String, icon: ImageVector) {
    var text by remember {
        mutableStateOf(uText)
    }

    OutlinedTextField(
        modifier = modifier,
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = false,
        maxLines = 5
    )

}
@Composable
fun AddNumberTextField(modifier: Modifier, what: String, uText: String, resId: Int) {
    var text by remember {
        mutableStateOf(uText)
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = {
            Text(text = what)
        },
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = resId), contentDescription = "Icon of $what")
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = false,
        maxLines = 5
    )

}

@Composable
fun AddNumberTextField(modifier: Modifier, what: String, uText: String) {
    var text by remember {
        mutableStateOf(uText)
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        onValueChange = { newText ->
            text = newText
        },
        label = {
            Text(text = what)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        singleLine = false,
        maxLines = 5
    )

}