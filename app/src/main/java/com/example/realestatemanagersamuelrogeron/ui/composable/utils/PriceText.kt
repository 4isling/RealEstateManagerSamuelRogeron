package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun PriceText(
    estatePrice: Int,
    modifier: Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    color: Color,
    style: TextStyle = LocalTextStyle.current,
    toEuro: Boolean = false,
){
    if (!toEuro){
        Text(
            text = "$estatePrice $",
            modifier = modifier,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = color,
            style = style,
        )
    }else{
        val price = (estatePrice * 0.92)
        Text(
            text = "$estatePrice €",
            modifier = modifier,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = color,
            style = style,
        )
    }

}