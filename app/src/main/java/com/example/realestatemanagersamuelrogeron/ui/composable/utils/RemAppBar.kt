package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
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
            IconButton(onClick = {
                toggleScreen(selectedScreen, onScreenSelected)
            }) {
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
            IconButton(
                onClick = onClickAdd,
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
fun RemBottomAppBarPreview() {
    AppTheme {
        var selectedScreen by remember { mutableStateOf("List") }
        RemBottomAppBar(
            selectedScreen = selectedScreen,
            onScreenSelected = { selectedScreen = it }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemDetailTopAppBar(
    title: String,
    modifier: Modifier,
    onBackPress: () -> Unit,
    onEditIconClick: () -> Unit,
    ) {
    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = {
                Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackPress()
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onEditIconClick() }
            ) {
                Icon(
                    imageVector = RemIcon.Edit,
                    contentDescription = null
                )
            }
        }
    )
}


@Preview
@Composable
fun RemTopAppBarPreview() {
    AppTheme {/*
        TopAppBar(
            title = "detail",
        )
        */
    }
}

@Composable
fun RemLeftBar(){

}
@Preview
@Composable
fun RemLeftBarPreview(){
    AppTheme {

    }
}