package com.example.realestatemanagersamuelrogeron.ui.composable.add_screen

import android.graphics.Bitmap
import android.media.Image
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.ui.theme.Shapes
import java.io.IOException

@Composable

fun AddListOfPictures( ){
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.1f)
            .border(2.dp, Color.Black, Shapes.medium)) {
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.1f)
                .border(border = BorderStroke(4.dp, Color.Black))
                .clickable{
                    onAddImageClick()
                }) {
            Icons.Default.Add
        }

    }



}

private fun onAddImageClick(){

}
/*
private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean{
    return try {

    } catch (e: IOException){
        e.printStackTrace()
        false
    }
}*/