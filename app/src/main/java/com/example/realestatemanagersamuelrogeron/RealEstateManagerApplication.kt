package com.example.realestatemanagersamuelrogeron

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RealEstateManagerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}