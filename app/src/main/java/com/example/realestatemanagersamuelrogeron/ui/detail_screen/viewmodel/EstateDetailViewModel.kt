package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateWithDetailUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetStaticMapUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.UpdateEstateUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateDetailViewModel @Inject constructor(
    private val getEstateWithDetailUseCaseImpl: GetEstateWithDetailUseCaseImpl,
    private val getStaticMapUseCaseImpl: GetStaticMapUseCaseImpl,
    private val updateEstateUseCaseImpl: UpdateEstateUseCaseImpl,
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
                        estate = estate,
                        isEuro = isEuro
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
        if (_estate.value.estate.lng != null && _estate.value.estate.lat != null) {
            try {
                val url = getStaticMapUseCaseImpl.invoke(_estate.value.estate)
                val estateUpdate = Estate(
                    estateId = _estate.value.estate.estateId,
                    title = _estate.value.estate.title,
                    typeOfEstate = _estate.value.estate.typeOfEstate,
                    typeOfOffer = _estate.value.estate.typeOfOffer,
                    etage = _estate.value.estate.etage,
                    address = _estate.value.estate.address,
                    zipCode = _estate.value.estate.zipCode,
                    city = _estate.value.estate.city,
                    region = _estate.value.estate.region,
                    country = _estate.value.estate.country,
                    description = _estate.value.estate.description,
                    addDate = _estate.value.estate.addDate,
                    sellDate = _estate.value.estate.sellDate,
                    agent = _estate.value.estate.agent,
                    price = _estate.value.estate.price,
                    surface = _estate.value.estate.surface,
                    nbRooms = _estate.value.estate.nbRooms,
                    status = _estate.value.estate.status,
                    isFav = _estate.value.estate.isFav,
                    lat = _estate.value.estate.lat,
                    lng = _estate.value.estate.lng,
                    staticMapUrl = url
                )
                updateEstateUseCaseImpl.invoke(estateUpdate)
            } catch (e: Exception) {
                Log.e("DetailViewModel.init", "getStaticMapUrl: $e")
            }
        }
    }

    fun onEditButtonClick() {
        // Implementation for edit button click
    }
}
