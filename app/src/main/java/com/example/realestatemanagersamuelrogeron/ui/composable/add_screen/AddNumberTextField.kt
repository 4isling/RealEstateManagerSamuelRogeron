package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import androidx.compose.foundation.text.KeyboardOptions
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