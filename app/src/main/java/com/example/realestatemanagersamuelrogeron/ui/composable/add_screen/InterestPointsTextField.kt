package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.view.KeyEvent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.R

@Composable
fun InterestPointsTextField(uText: String = "", modifier: Modifier) {

    Column() {
        var interestPoints by remember {
            mutableStateOf<List<String>>(value = emptyList())
        }

        var text by remember {
            mutableStateOf(uText)
        }

        OutlinedTextField(
            value = text,
            onValueChange = { newText ->
                text = newText
            },
            label = {
                Text(text = "Interest")
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    interestPoints = interestPoints.toMutableList().also { it.add(text) }
                    text = ""
                }
            ),
            singleLine = true,
            modifier = modifier
                .padding(horizontal = 0.dp, vertical = 8.dp),
        )
        LazyRow(
            content =
            {
                items(interestPoints) { point ->

                    Row(modifier= Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Gray)
                        ) {
                        Text(text = point)
                        IconButton(onClick = {
                            interestPoints =
                                interestPoints.toMutableList().also { it.remove(point) }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_circle_24),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        )
    }
}