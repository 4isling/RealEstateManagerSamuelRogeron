package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.domain.model.EstateWithPictures
import java.lang.Exception

sealed class ListViewState {
    object Loading : ListViewState()

    object Success: ListViewState()

    data class Error(val exception: String): ListViewState()

    
}