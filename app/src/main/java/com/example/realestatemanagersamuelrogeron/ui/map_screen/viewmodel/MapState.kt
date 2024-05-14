package com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures

sealed class MapState{
    object Loading : MapState()
    data class Success(val estates: List<EstateWithPictures>) : MapState()

    data class Error(val exception: Exception): MapState()

}