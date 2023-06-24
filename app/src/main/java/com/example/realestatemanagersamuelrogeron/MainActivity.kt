package com.example.realestatemanagersamuelrogeron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.ui.navigation.Navigation
import com.example.realestatemanagersamuelrogeron.ui.theme.RealEstateManagerSamuelRogeronTheme
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstateViewModel
import com.example.realestatemanagersamuelrogeron.ui.viewmodel.EstatesListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

                Navigation(navController)

        }
    }
}