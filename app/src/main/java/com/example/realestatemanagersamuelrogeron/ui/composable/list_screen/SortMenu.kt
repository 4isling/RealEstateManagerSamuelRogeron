package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.realestatemanagersamuelrogeron.SortType

@Composable
fun SortMenu(
    sortOptions: List<SortType>,
    onSortOptionSelected: (SortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    var choices by remember {
        mutableStateOf(sortOptions)
    }

    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.Filter,
            contentDescription = "Filter Icons"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        choices.forEach { option ->
            DropdownMenuItem(
                text = {
                    Text(text = option.name)
                },
                onClick = {
                onSortOptionSelected(option)
                expanded = false
                }
            )
        }
    }
}