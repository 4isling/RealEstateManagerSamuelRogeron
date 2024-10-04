package com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCase
import com.example.realestatemanagersamuelrogeron.utils.UriPermissionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEstateViewModel @Inject constructor(
    private val addEstateUseCase: AddEstateUseCase,
    private val addInterestPointUseCase: AddInterestPointUseCase,
    private val getCurrencyPreferenceUseCase: GetCurrencyPreferenceUseCase,
    private val getAllInterestPointUseCase: GetAllInterestPointUseCase,
    private val uriPermissionHelper: UriPermissionHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEstateState())
    val uiState: StateFlow<AddEstateState> = _uiState.asStateFlow()

    private val _toastEvent = MutableSharedFlow<String>()
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    init {
        loadInterestPoints()
        loadCurrencyPreference()
    }

    private fun loadInterestPoints() {
        viewModelScope.launch {
            getAllInterestPointUseCase.invoke().collect { interestPoints ->
                _uiState.update { currentState ->
                    currentState.copy(allInterestPoints = interestPoints)
                }
            }
        }
    }

    private fun loadCurrencyPreference() {
        viewModelScope.launch {
            getCurrencyPreferenceUseCase.invoke().collect { isEuro ->
                _uiState.update { it.copy(isEuro = isEuro) }
            }
        }
    }

    fun onFieldChange(field: String, value: String) {
        _uiState.update { currentState ->
            val updatedEstate = currentState.estateWithDetails.estate.copy(
                title = if (field == "title") value else currentState.estateWithDetails.estate.title,
                typeOfEstate = if (field == "type") value else currentState.estateWithDetails.estate.typeOfEstate,
                typeOfOffer = if (field == "offer") value else currentState.estateWithDetails.estate.typeOfOffer,
                etage = if (field == "etage") value else currentState.estateWithDetails.estate.etage,
                address = if (field == "address") value else currentState.estateWithDetails.estate.address,
                zipCode = if (field == "zipCode") value else currentState.estateWithDetails.estate.zipCode,
                city = if (field == "city") value else currentState.estateWithDetails.estate.city,
                region = if (field == "region") value else currentState.estateWithDetails.estate.region,
                country = if (field == "country") value else currentState.estateWithDetails.estate.country,
                description = if (field == "description") value else currentState.estateWithDetails.estate.description,
                price = if (field == "price") value.toIntOrNull()
                    ?: currentState.estateWithDetails.estate.price else currentState.estateWithDetails.estate.price,
                surface = if (field == "surface") value.toIntOrNull()
                    ?: currentState.estateWithDetails.estate.surface else currentState.estateWithDetails.estate.surface,
                nbRooms = if (field == "nbRooms") value.toIntOrNull()
                    ?: currentState.estateWithDetails.estate.nbRooms else currentState.estateWithDetails.estate.nbRooms
            )
            val updatedEstateWithDetails =
                currentState.estateWithDetails.copy(estate = updatedEstate)
            currentState.copy(
                estateWithDetails = updatedEstateWithDetails,
            )
        }
    }

    fun onMediaPicked(medias: List<Uri>) {
        medias.forEach { uri ->
            uriPermissionHelper.takePersistableUriPermission(uri)
        }
        _uiState.update { currentState ->
            currentState.copy(newMediaUris = currentState.newMediaUris + medias)
        }
    }

    fun onMediaRemoved(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(newMediaUris = currentState.newMediaUris - uri)
        }
    }

    fun onImageCaptured(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(newMediaUris = currentState.newMediaUris + uri)
        }
    }

    fun onInterestPointSelected(selectedPoints: List<EstateInterestPoints>) {
        _uiState.update { currentState ->
            val updatedEstateWithDetails = currentState.estateWithDetails.copy(
                estateInterestPoints = selectedPoints
            )
            currentState.copy(estateWithDetails = updatedEstateWithDetails)
        }
    }

    fun removeSelectedInterestPoint(interestPoint: EstateInterestPoints) {
        _uiState.update { currentState ->
            val updatedInterestPoints =
                currentState.estateWithDetails.estateInterestPoints - interestPoint
            val updatedEstateWithDetails = currentState.estateWithDetails.copy(
                estateInterestPoints = updatedInterestPoints
            )
            currentState.copy(estateWithDetails = updatedEstateWithDetails)
        }
    }

    fun onCreateNewInterestPoint(name: String, iconCode: Int) {
        viewModelScope.launch {
            val newInterestPoint =
                EstateInterestPoints(interestPointsName = name, iconCode = iconCode)
            addInterestPointUseCase.invoke(newInterestPoint)
            loadInterestPoints() // Reload all interest points after adding a new one
        }
    }

    fun onSaveButtonClick() {
        _uiState.update { currentState ->
            currentState.copy(
                titleError = currentState.estateWithDetails.estate.title.isBlank(),
                typeError = currentState.estateWithDetails.estate.typeOfEstate.isBlank(),
                offerError = currentState.estateWithDetails.estate.typeOfOffer.isBlank(),
                priceError = currentState.estateWithDetails.estate.price <= 0,
                surfaceError = currentState.estateWithDetails.estate.surface <= 0,
                nbRoomsError = currentState.estateWithDetails.estate.nbRooms <= 0,
                etagesError = currentState.estateWithDetails.estate.etage.isBlank(),
                addressError = currentState.estateWithDetails.estate.address.isBlank(),
                zipCodeError = currentState.estateWithDetails.estate.zipCode.isBlank(),
                cityError = currentState.estateWithDetails.estate.city.isBlank(),
                regionError = currentState.estateWithDetails.estate.region.isBlank(),
                countryError = currentState.estateWithDetails.estate.country.isBlank(),
                descriptionError = currentState.estateWithDetails.estate.description.isBlank(),
                mediaError = currentState.newMediaUris.isEmpty(),
                interestPointError = currentState.estateWithDetails.estateInterestPoints.isEmpty()
            )
        }
        if (isValidEstateData()) {
            _uiState.update { currentState ->
                val updatedEstate = currentState.estateWithDetails.estate.copy(
                    price = formatPrice(
                        currentState.estateWithDetails.estate.price,
                        currentState.isEuro
                    )
                )

                val updatedEstateWithDetails =
                    currentState.estateWithDetails.copy(estate = updatedEstate)

                currentState.copy(
                    estateWithDetails = updatedEstateWithDetails,
                )
            }
            saveEstate()
        }

    }

    private fun isValidEstateData(): Boolean {
        val currentState = _uiState.value
        return !currentState.titleError && !currentState.typeError && !currentState.offerError &&
                !currentState.priceError && !currentState.surfaceError && !currentState.nbRoomsError &&
                !currentState.etagesError && !currentState.addressError && !currentState.zipCodeError &&
                !currentState.cityError && !currentState.regionError && !currentState.countryError &&
                !currentState.descriptionError && !currentState.mediaError && !currentState.interestPointError

    }

    private fun saveEstate() {
        viewModelScope.launch {
            val currentState = _uiState.value
            try {
                 addEstateUseCase.invoke(
                    entry = currentState.estateWithDetails.estate,
                    interestPoints = currentState.estateWithDetails.estateInterestPoints,
                    pics = currentState.newMediaUris
                ).collect { estateId ->
                    if (estateId > 0L) {
                        _uiState.update { it.copy(isEstateSaved = true) }
                        _toastEvent.emit("Estate added successfully")
                    } else {
                        _uiState.update { it.copy(error = "Failed to save estate") }
                        _toastEvent.emit("Failed to add estate")
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error saving estate: ${e.message}") }
                _toastEvent.emit("Error: ${e.message}")
            }
        }
    }

    private fun formatPrice(price: Int, isEuro: Boolean): Int {
        return if (isEuro) {
            val euroPrice = (price * 0.92).toInt() // Assuming 1 USD = 0.92 EUR
            euroPrice
        } else {
            price
        }
    }
}