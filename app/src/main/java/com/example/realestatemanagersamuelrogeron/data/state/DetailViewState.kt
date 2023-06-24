package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import java.lang.Exception

sealed class DetailViewState {
    object Loading : DetailViewState()

    data class Success(val estate: Estate) : DetailViewState()

    data class Error(val exception: String) : DetailViewState()
}