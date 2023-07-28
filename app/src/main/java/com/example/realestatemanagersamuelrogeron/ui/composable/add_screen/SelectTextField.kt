package com.srogeron.testcompose.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SelectTextField(what: String, choices: List<String>, modifier: Modifier, userChoice: String) {
    /*
        // Declaring a boolean value to store
        // the expanded state of the Text Field
        var mExpanded by remember { mutableStateOf(false) }

        // Create a list of cities

        var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

        // Up Icon when expanded and down icon when collapsed
        val icon = if (mExpanded)
            Icons.Filled.KeyboardArrowUp
        else
            Icons.Filled.KeyboardArrowDown

        Column(modifier = modifier) {

            // Create an Outlined Text Field
            // with icon and not expanded
            OutlinedTextField(
                value = userChoice,
                onValueChange = { userChoice = it },
                modifier = Modifier
                    .clickable { mExpanded = !mExpanded }
                    .onGloballyPositioned { coordinates ->
                        // This value is used to assign to
                        // the DropDown the same width
                        mTextFieldSize = coordinates.size.toSize()
                    },

                label = {Text(what)},
                trailingIcon = {
                    Icon(icon,"contentDescription")
                },
                enabled = false

            )

            // Create a drop-down menu with list of cities,
            // when clicked, set the Text Field text as the choice selected
            DropdownMenu(
                expanded = mExpanded,
                onDismissRequest = { mExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
            ) {
                choices.forEach { uChoice ->
                    DropdownMenuItem(onClick = {
                        userChoice = uChoice
                        mExpanded = false
                    }) {
                        Text(text = uChoice)
                    }
                }
            }
        }

    var selectedOption by remember {
        mutableStateOf("")
    }
    val options = listOf("Rent", "Sell", "Rent and Sell")

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = selectedOption,
            onValueChange = { selectedOption = it },
            modifier = Modifier.clickable {
                // Open the dropdown menu
                // when the TextField is clicked
            }
        )
        DropdownMenu(
            expanded = false, // Control the dropdown menu visibility
            onDismissRequest = { /* Dismiss the dropdown menu */ }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    onClick = {
                        selectedOption = option
                        // Perform action based on the selected option
                    }
                ) {
                    Text(text = option)
                }
            }
        }
    }*/
}
