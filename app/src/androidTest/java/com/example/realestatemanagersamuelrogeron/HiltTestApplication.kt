package com.example.realestatemanagersamuelrogeron

import android.app.Application
import dagger.hilt.android.testing.CustomTestApplication


@CustomTestApplication(Application::class)
interface HiltTestApplication