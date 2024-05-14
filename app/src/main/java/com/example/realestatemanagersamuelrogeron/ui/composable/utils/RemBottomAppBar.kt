package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.utils.RemIcon

@Composable
fun RemBottomAppBar(
    selectedScreen: String,
    onScreenSelected: (String) -> Unit,
    onClickSetting: () -> Unit = {},
    onClickAdd: () -> Unit = {},
) {
    BottomAppBar(
        windowInsets = BottomAppBarDefaults.windowInsets,
        modifier = Modifier.fillMaxWidth(),
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.onPrimary),
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        actions = {
            // Toggle button between List and Map views
            IconButton(onClick = { toggleScreen(selectedScreen, onScreenSelected) }) {
                val icon = if (selectedScreen == "List") RemIcon.Map else RemIcon.List
                val label = if (selectedScreen == "List") "Map" else "List"
                Icon(imageVector = icon, contentDescription = "Switch to $label")
            }
            // Settings button
            IconButton(onClick = {
                onClickSetting()
            }) {
                Icon(imageVector = RemIcon.Setting, contentDescription = "Setting")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickAdd,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = RemIcon.Add, contentDescription = "Add")
            }
        },
    )
}

private fun toggleScreen(currentScreen: String, onScreenSelected: (String) -> Unit) {
    if (currentScreen == "List") {
        onScreenSelected("Map")
    } else {
        onScreenSelected("List")
    }
}

@Preview
@Composable
fun RemBottomAppBarPreview(){
    MaterialTheme {
        var selectedScreen by remember { mutableStateOf("List") }
        RemBottomAppBar(
            selectedScreen = selectedScreen,
            onScreenSelected = { selectedScreen = it }
        )
    }
}
