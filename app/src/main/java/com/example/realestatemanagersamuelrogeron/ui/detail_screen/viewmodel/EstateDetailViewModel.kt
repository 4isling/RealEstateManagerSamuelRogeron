package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateWithDetailUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetStaticMapUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateDetailViewModel @Inject constructor(
    private val getEstateWithDetailUseCaseImpl: GetEstateWithDetailUseCaseImpl,
    private val getStaticMapUseCaseImpl: GetStaticMapUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl,
    private val getCurrencyPreferenceUseCaseImpl: GetCurrencyPreferenceUseCaseImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val uiState: StateFlow<DetailViewState> = _uiState.asStateFlow()

    private val _estate = MutableStateFlow<EstateWithDetails>(
        EstateWithDetails(
            estate = Estate(
                0L,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0,
                "",
                0,
                0,
                0,
                true,
                false
            ),
            estatePictures = emptyList(),
            estateInterestPoints = emptyList()
        )
    )

    init {
        val estateId = savedStateHandle.get<Long>("estateId")
        println("estateId: " + estateId)
        if (estateId != null) {
            viewModelScope.launch {
                try {
                    _uiState.value = DetailViewState.Loading
                    val isEuro = getCurrencyPreferenceUseCaseImpl.invoke().first()
                    // Fetch estate details
                    val estate = getEstateWithDetailUseCaseImpl.invoke(estateId).first()
                    _estate.value = estate
                    _uiState.value = DetailViewState.Success(
                        estateWithDetails = estate,
                        isEuro = isEuro,
                    )

                    addLatLngToEstate()
                    getStaticMapUrl()
                } catch (e: Exception) {
                    _uiState.value = DetailViewState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    private fun addLatLngToEstate() {
        viewModelScope.launch {
            addLatLngToEstatesUseCaseImpl.invoke()
        }
    }

    private suspend fun getStaticMapUrl() {
        _uiState.update { currentState ->
            if (currentState is DetailViewState.Success){
                val url = getStaticMapUseCaseImpl.invoke(entry = currentState.estateWithDetails.estate).toString()
                if (url != null){
                    val updateEstate = currentState.estateWithDetails.estate.copy(staticMapUrl = url)
                    val updateEstateWithDetails =
                    currentState.estateWithDetails.copy(estate = updateEstate)
                    currentState.copy(estateWithDetails = updateEstateWithDetails)
                }else{
                    currentState
                }
            }else {
                currentState
            }

        }
    }
}
