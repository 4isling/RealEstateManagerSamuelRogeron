package com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures

sealed class ListViewState {
    object Loading : ListViewState()

    data class Success(val estates: List<EstateWithPictures>) : ListViewState()

    data class Error(val exception: String): ListViewState()

}