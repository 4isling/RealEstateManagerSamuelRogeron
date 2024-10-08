package com.example.realestatemanagersamuelrogeron

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.example.realestatemanagersamuelrogeron.ui.navigation.Navigation
import com.example.realestatemanagersamuelrogeron.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            AppTheme {
                val windowSizeClass = calculateWindowSizeClass(
                    activity = this
                )
                Navigation(
                    windowSizeClass
                )
            }
        }
    }
}