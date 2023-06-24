package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures

sealed class AddViewState {
    object Loading: AddViewState()
    data class Success(val estate: Estate, val estatePics: List<EstatePictures>, val estateInterestPoints: List<EstateInterestPoints>) : AddViewState()
    data class Error(val exception: String) : AddViewState()
}