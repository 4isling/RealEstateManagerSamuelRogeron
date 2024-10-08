package com.example.realestatemanagersamuelrogeron.ui.shared.viewmodel

import android.location.Location
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter

sealed class SharedEstateViewState {
    object Loading : SharedEstateViewState()
    data class Success(
        val estates: List<EstateWithDetails>,
        val estateSelected: EstateWithDetails? = null,
        val userLocation: Location? = null,
        val isEuro: Boolean = false,
        val estateFilter: EstateFilter
    ) : SharedEstateViewState()
    data class Error(val exception: String) : SharedEstateViewState()
}