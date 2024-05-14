package com.example.realestatemanagersamuelrogeron.ui.add_screen.composable

import android.Manifest
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.realestatemanagersamuelrogeron.utils.PermissionUtils

@Composable
fun MediaPicker(onMediaSelected: (List<Uri>) -> Unit) {
    val context = LocalContext.current
    val picker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris: List<Uri> ->
            if (uris.isNotEmpty()) {
                onMediaSelected(uris)
            }
        }
    )
    val openMediaPicker = remember {
        mutableStateOf(false)
    }

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    PermissionUtils.requestPermission(permission = permission) {
        if (!it) {
            openMediaPicker.value = true
            picker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

        } else {
            Toast.makeText(
                context,
                "Camera permission is required to use the camera.",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}