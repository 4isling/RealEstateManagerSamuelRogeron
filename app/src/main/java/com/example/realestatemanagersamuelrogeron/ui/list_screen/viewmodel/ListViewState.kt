package com.example.realestatemanagersamuelrogeron.ui.list_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter

sealed class ListViewState {
    object Loading : ListViewState()

    data class Success(
        val estates: List<EstateWithDetails>,
        val estateFilter: EstateFilter,
        val isEuro: Boolean
        ) : ListViewState()

    data class Error(val exception: String): ListViewState()

}