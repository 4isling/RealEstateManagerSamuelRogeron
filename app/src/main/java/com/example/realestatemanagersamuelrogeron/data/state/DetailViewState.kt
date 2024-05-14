package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate

sealed class DetailViewState {
    object Loading : DetailViewState()

    data class Success(val estate: Estate) : DetailViewState()

    data class Error(val exception: String) : DetailViewState()


}