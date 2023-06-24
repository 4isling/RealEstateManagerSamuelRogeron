package com.example.realestatemanagersamuelrogeron.ui.composable.list_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.realestatemanagersamuelrogeron.R
import com.example.realestatemanagersamuelrogeron.SortType

@Composable
fun SortMenu(
    sortOptions: List<SortType>,
    onSortOptionSelected: (SortType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_filter_alt_24),
            contentDescription = "Filter Icons"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        sortOptions.forEach { option ->
            DropdownMenuItem(onClick = {
                onSortOptionSelected(option)
                expanded = false
            }) {
                Text(text = option.name)
            }
        }
    }
}