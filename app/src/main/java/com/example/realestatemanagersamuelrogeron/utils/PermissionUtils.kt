package com.example.realestatemanagersamuelrogeron.utils

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class PermissionUtils {
    companion object {
        @Composable
        fun requestPermission(
            permission: String,
            onPermissionResult: (Boolean) -> Unit
        ): Boolean {
            var hasPermission by remember { mutableStateOf(false) }
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted: Boolean ->
                    hasPermission = isGranted
                    onPermissionResult(isGranted)
                }
            )

            // Trigger permission request
            LaunchedEffect(Unit) {
                permissionLauncher.launch(permission)
            }

            return hasPermission
        }
    }
}