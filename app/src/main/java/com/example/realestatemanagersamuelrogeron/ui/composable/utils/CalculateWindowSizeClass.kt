package com.example.realestatemanagersamuelrogeron.ui.composable.utils

import android.os.Build
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun calculateWindowSizeClass(): WindowSizeClass {
    val configuration = LocalConfiguration.current
    val windowMetrics = LocalContext.current.getSystemService(WindowManager::class.java)
        .currentWindowMetrics

    val widthDp = windowMetrics.bounds.width() / configuration.densityDpi.toFloat()
    val heightDp = windowMetrics.bounds.height() / configuration.densityDpi.toFloat()

    return remember(configuration) {
        WindowSizeClass.calculateFromSize(DpSize(widthDp.dp, heightDp.dp))
    }
}