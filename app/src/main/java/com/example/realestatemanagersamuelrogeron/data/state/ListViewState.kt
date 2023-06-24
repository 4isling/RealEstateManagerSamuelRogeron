package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import java.lang.Exception

sealed class ListViewState {
    object Loading : ListViewState()

    data class Success(val estates: List<Estate>): ListViewState()

    data class Error(val exception: String): ListViewState()
}