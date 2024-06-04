package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

sealed class DetailViewState {
    object Loading : DetailViewState()

    data class Success(val estate: Estate,val medias: List<EstateMedia>, val interestPoints: List<EstateInterestPoints>) : DetailViewState()

    data class Error(val exception: String) : DetailViewState()


}