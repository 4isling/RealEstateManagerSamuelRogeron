package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun SelectTextField(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: List<String>,
    label: String = "Select an option"
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .testTag("select_text_field_text_field")
                .clickable {
                    expanded = !expanded
                },
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    Modifier.clickable {
                        expanded = !expanded
                    }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.testTag("select_text_field_dropdown_menu_item"),
                    text = {
                        Text(text = option)
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
