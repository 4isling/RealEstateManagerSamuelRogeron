package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaPickerDialog(
    onClickCamera: () -> Unit = {},
    onClickAlbum: () -> Unit = {},
    onClickCancel: () -> Unit = {},
) {
    BasicAlertDialog(
        onDismissRequest = onClickCancel,
    ) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primaryContainer,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Pick Media from:",
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { onClickCamera() },
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(text = "Camera")
                }
                Button(onClick = { onClickAlbum() },
                    modifier = Modifier
                        .align(Alignment.End)
                        .fillMaxWidth(0.5f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Text(text = "Album")
                }
            }
        }
    }
}

@Preview
@Composable
fun MediaPickerDialogPreview(){
    AppTheme {
        MediaPickerDialog()
    }
}