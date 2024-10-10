package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails

sealed class DetailViewState {
    object Loading : DetailViewState()

    data class Success(
        val estateWithDetails: EstateWithDetails,
        val isEuro: Boolean
    ) : DetailViewState()

    data class Error(val exception: String) : DetailViewState()
}