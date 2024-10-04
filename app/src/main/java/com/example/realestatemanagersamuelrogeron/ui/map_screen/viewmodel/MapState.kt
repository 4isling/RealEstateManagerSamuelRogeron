package com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel

import android.location.Location
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails

sealed class MapState{
    object Loading : MapState()
    data class Success(
        val estates: List<EstateWithDetails>,
        val estateSelected: EstateWithDetails? = null,
        val userLocation: Location? = null,
        val isEuro: Boolean = false
    ) : MapState()

    data class Error(val exception: String): MapState()

}