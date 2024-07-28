package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.ui.add_screen.composable.SelectTextField
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    initialFilter: EstateFilter,
    onFilterChange: (EstateFilter) -> Unit,
    onDismiss: () -> Unit
) {
    val priceRange =
        remember { mutableStateOf(initialFilter.minPrice.toFloat()..initialFilter.maxPrice.toFloat()) }
    val surfaceRange =
        remember { mutableStateOf(initialFilter.minSurface.toFloat()..initialFilter.maxSurface.toFloat()) }
    var minMediaCount by remember { mutableStateOf(initialFilter.minMediaCount) }
    var typeOfEstate by remember { mutableStateOf(initialFilter.typeOfEstate ?: "") }
    var typeOfOffer by remember { mutableStateOf(initialFilter.typeOfOffer ?: "") }
    var minPrice by remember { mutableStateOf(initialFilter.minPrice) }
    var maxPrice by remember { mutableStateOf(initialFilter.maxPrice) }
    var etage by remember { mutableStateOf(initialFilter.etage ?: "") }
    var city by remember { mutableStateOf(initialFilter.city ?: "") }
    var region by remember { mutableStateOf(initialFilter.region ?: "") }
    var country by remember { mutableStateOf(initialFilter.country ?: "") }
    var minSurface by remember { mutableStateOf(initialFilter.minSurface) }
    var maxSurface by remember { mutableStateOf(initialFilter.maxSurface) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Filter Estates") },
        text = {
            Surface(modifier = Modifier.wrapContentSize()) {
                LazyColumn {
                    item {
                        Text(text = "Min picture")
                        Slider(
                            value = minMediaCount.toFloat(),
                            onValueChange = { minMediaCount = it.toInt() },
                            steps = 30,
                            valueRange = 0f..30f
                        )
                    }
                    item {
                        SelectTextField(
                            label = "Type",
                            selectedOption = typeOfEstate,
                            onOptionSelected = { newValue ->
                                typeOfEstate = if (newValue == "Any") {
                                    ""
                                } else {
                                    newValue
                                }
                            },
                            options = listOf(
                                "Apartment",
                                "House",
                                "Garage",
                                "Land-field",
                                "Warehouse",
                                "Any"
                            )
                        )
                    }
                    item {
                        SelectTextField(
                            label = "Offer",
                            selectedOption = typeOfOffer,
                            onOptionSelected = { newValue -> typeOfOffer = newValue },
                            options = listOf("Rent", "Sell", "Rent or Sell"),
                        )

                        Text(
                            text = "Price",
                            modifier = Modifier.padding(0.dp, 4.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.SpaceAround
                        ) {
                            var minPriceError = false

                            OutlinedTextField(
                                modifier = Modifier.fillParentMaxWidth(0.5f),
                                value = minPrice.toString(),
                                onValueChange = {
                                    minPrice = if (it != "") {
                                        if (maxPrice < minPrice) {
                                            minPriceError = true
                                        }
                                        it.toInt()
                                    } else {
                                        minPriceError = false
                                        0
                                    }
                                },
                                suffix = {
                                    Text(
                                        text = if (typeOfOffer == "Rent") {
                                            " $/m"
                                        } else {
                                            " $"
                                        }
                                    )
                                },
                                isError = minPriceError,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                            /*
                            Text(text = minPrice.toString())
                            RangeSlider(
                                value = priceRange.value,
                                onValueChange = { newRange ->
                                    priceRange.value = newRange
                                    minPrice = newRange.start.toInt()
                                    maxPrice = newRange.endInclusive.toInt()
                                },
                                valueRange = 0f..10000000f,
                                steps = 500,
                                colors = SliderDefaults.colors(
                                    activeTrackColor = MaterialTheme.colorScheme.primary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.24f
                                    ),
                                    activeTickColor = MaterialTheme.colorScheme.primary,
                                    inactiveTickColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.24f
                                    ),
                                    thumbColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(text = maxPrice.toString())*/
                            var maxPriceError = false
                            OutlinedTextField(
                                modifier = Modifier.wrapContentWidth(),
                                value = maxPrice.toString(),
                                onValueChange = {
                                    maxPrice = if (it != "") {
                                        if (maxPrice < minPrice) {
                                            maxPriceError = true
                                        }
                                        it.toInt()
                                    } else {
                                        maxPriceError = false
                                        100000000
                                    }
                                },
                                isError = maxPriceError,
                                suffix = {
                                    Text(
                                        text = if (typeOfOffer == "Rent") {
                                            " $/m"
                                        } else {
                                            " $"
                                        }
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                        }

                    }
                    item {
                        TextField(
                            value = etage,
                            onValueChange = { etage = it },
                            label = { Text("Etage") }
                        )
                    }
                    item {
                        TextField(
                            value = city,
                            onValueChange = { city = it },
                            label = { Text("City") }
                        )
                        TextField(
                            value = region,
                            onValueChange = { region = it },
                            label = { Text("Region") }
                        )
                        TextField(
                            value = country,
                            onValueChange = { country = it },
                            label = { Text("Country") }
                        )
                    }/*
                    item {
                        TextField(
                            modifier = Modifier.clickable {
                                showDatePicker = true
                            },
                            value = addDate,
                            onValueChange = { addDate = it },
                            label = { Text("Add Date") },
                        )
                    }*/
                    item {
                        Text(text = "Surface")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            var minSurfaceError = false
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(0.5f),
                                value = minSurface.toString(),
                                onValueChange = {
                                    minSurface = if (it != "") {
                                        if (maxSurface < minSurface) {
                                            minSurfaceError = true
                                        }
                                        it.toInt()
                                    } else {
                                        minSurfaceError = false
                                        0
                                    }
                                },
                                isError = minSurfaceError,
                                suffix = { Text(text = " m²") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                            var maxSurfaceError = false
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = maxSurface.toString(),
                                onValueChange = {
                                    minSurface = if (it != "") {
                                        if (maxSurface < minSurface) {
                                            maxSurfaceError = true
                                        }
                                        it.toInt()
                                    } else {
                                        maxSurfaceError = false
                                        100000000
                                    }
                                },
                                isError = maxSurfaceError,
                                suffix = { Text(text = " m²") },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                            )
                            /*
                            Text(text = minSurface.toString())
                            RangeSlider(
                                value = surfaceRange.value,
                                onValueChange = { newRange -> surfaceRange.value = newRange },
                                valueRange = 0f..100f,
                                steps = 4,
                                colors = SliderDefaults.colors(
                                    activeTrackColor = MaterialTheme.colorScheme.primary,
                                    inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.24f
                                    ),
                                    activeTickColor = MaterialTheme.colorScheme.primary,
                                    inactiveTickColor = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.24f
                                    ),
                                    thumbColor = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                text = maxSurface.toString()
                            )*/
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onFilterChange(
                    EstateFilter(
                        minMediaCount = minMediaCount.toInt(),
                        typeOfEstate = if (typeOfEstate.isNotBlank()) typeOfEstate else null,
                        typeOfOffer = if (typeOfOffer == "Rent or Sell" || typeOfOffer == "") null else typeOfOffer,
                        minPrice = minPrice.toInt(),
                        maxPrice = maxPrice.toInt(),
                        etage = if (etage.isNotBlank()) etage else null,
                        city = if (city.isNotBlank()) city else null,
                        region = if (region.isNotBlank()) region else null,
                        country = if (country.isNotBlank()) country else null,
                        minSurface = minSurface.toInt(),
                        maxSurface = maxSurface.toInt(),
                    )
                )
                onDismiss()
            }) {
                Text("Apply")
            }

        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )

    if (showDatePicker) {
        MyDatePickerDialog()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerView() {
    val datePickerState =
        rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        DatePicker(
            state = datePickerState
        )
        Spacer(
            modifier = Modifier.height(
                32.dp
            )
        )
        Text(
            text = selectedDate.toString(),
            color = Color.Red
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState =
        rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun MyDatePickerDialog() {
    var date by remember {
        mutableStateOf("Open date picker dialog")
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    Box(contentAlignment = Alignment.Center) {
        Button(onClick = { showDatePicker = true }) {
            Text(text = date)
        }
    }

    if (showDatePicker) {
        MyDatePickerDialog(
            onDateSelected = { date = it },
            onDismiss = { showDatePicker = false }
        )
    }
}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun FilterDialogPreview() {
    AppTheme {
        FilterDialog(
            initialFilter = EstateFilter(
                minMediaCount = 5,
                typeOfEstate = "Apartment",
                typeOfOffer = "Rent",
                minPrice = 1000,
                maxPrice = 5000,
                etage = "2",
                city = "Paris",
                region = "Île-de-France",
                country = "France",
                minSurface = 30,
                maxSurface = 120,
            ),
            onFilterChange = {},
            onDismiss = {}
        )
    }
}