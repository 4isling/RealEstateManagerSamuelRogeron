package com.example.realestatemanagersamuelrogeron.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class ScreenType {
    PHONE,
    TABLET,
    PC
}

@Composable
fun getScreenType(): ScreenType {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    return when {
        screenWidthDp >= 840 -> ScreenType.PC      // Adjust threshold as needed
        screenWidthDp >= 600 -> ScreenType.TABLET
        else -> ScreenType.PHONE
    }
}