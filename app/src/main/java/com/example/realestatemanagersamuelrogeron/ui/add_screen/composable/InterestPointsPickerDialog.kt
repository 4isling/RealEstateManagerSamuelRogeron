package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.predefinedInterestPoints

@Composable
fun InterestPointsPickerDialog(
    interestPoints: List<EstateInterestPoints>,
    initiallySelectedPoints: List<EstateInterestPoints>,
    onDismiss: () -> Unit,
    onCreateInterestPoint: (String, Int) -> Unit,
    onPointsSelected: (List<EstateInterestPoints>) -> Unit
) {
    val selectedPoints = remember { mutableStateListOf<EstateInterestPoints>().apply { addAll(initiallySelectedPoints) } }
    val (newPointName, setNewPointName) = remember { mutableStateOf("") }
    val (selectedIconCode, setSelectedIconCode) = remember { mutableStateOf(0) }
    val allIconCodes = RemIcon.iconMapping.keys.toList()

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Select Interest Points") },
        text = {
            Column {
                LazyColumn {
                    items(interestPoints) { point ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedPoints.contains(point),
                                onCheckedChange = { checked ->
                                    if (checked) {
                                        selectedPoints.add(point)
                                    } else {
                                        selectedPoints.remove(point)
                                    }
                                }
                            )
                            Icon(imageVector = RemIcon.iconMapping[point.iconCode]!!, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = point.interestPointsName)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newPointName,
                    onValueChange = setNewPointName,
                    label = { Text("New Interest Point") },
                    singleLine = true
                )
                LazyRow {
                    items(allIconCodes) { iconCode ->
                        Icon(
                            imageVector = RemIcon.iconMapping[iconCode]!!,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .size(40.dp)
                                .clickable {
                                    setSelectedIconCode(iconCode)
                                }
                                .background(if (iconCode == selectedIconCode) MaterialTheme.colorScheme.primary else Color.Transparent)
                        )
                    }
                }
                Button(onClick = {
                    onCreateInterestPoint(newPointName, selectedIconCode)
                    setNewPointName("")
                    setSelectedIconCode(0)
                }) {
                    Text("Create Interest Point")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onPointsSelected(selectedPoints.toList())
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Composable
@Preview
fun InterestPointsPickerDialogPreview(){
    InterestPointsPickerDialog(
        interestPoints =  predefinedInterestPoints,
        onCreateInterestPoint = { _, _ -> },
        onDismiss = {},
        onPointsSelected = {},
        initiallySelectedPoints = emptyList()
    )
}
