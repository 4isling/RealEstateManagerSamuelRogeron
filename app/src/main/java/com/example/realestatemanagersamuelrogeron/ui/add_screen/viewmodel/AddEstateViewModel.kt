package com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.utils.RemIcon
import com.example.realestatemanagersamuelrogeron.utils.RemIcon.iconMapping
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEstateViewModel @Inject constructor(
    private val addEstateUseCase: AddEstateUseCase,
    private val addInterestPointUseCase: AddInterestPointUseCase,
    private val estateRepository: EstateRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEstateState())
    val uiState: StateFlow<AddEstateState> = _uiState.asStateFlow()

    init {
        loadInterestPoints()
    }

    private fun loadInterestPoints() {
        viewModelScope.launch {
            estateRepository.getAllInterestPoints().collect { interestPoints ->
                _uiState.update { currentState ->
                    currentState.copy(
                        interestPoints = interestPoints.map {
                            it
                        }
                    )
                }
            }
        }
    }

    fun onFieldChange(field: String, newValue: String) {
        _uiState.update { currentState ->
            when (field) {
                "title" -> currentState.copy(title = newValue)
                "type" -> currentState.copy(type = newValue)
                "offer" -> currentState.copy(offer = newValue)
                "description" -> currentState.copy(description = newValue)
                "etage" -> currentState.copy(etages = newValue)
                "address" -> currentState.copy(address = newValue)
                "zipCode" -> currentState.copy(zipCode = newValue)
                "city" -> currentState.copy(city = newValue)
                "sellingPrice" -> currentState.copy(sellingPrice = newValue)
                "rent" -> currentState.copy(rent = newValue)
                "surface" -> currentState.copy(surface = newValue)
                "nbRooms" -> currentState.copy(nbRooms = newValue)
                else -> currentState
            }
        }
    }

    fun onMediaPicked(medias: List<Uri>) {
        _uiState.update { currentState ->
            currentState.copy(mediaSelected = medias)
        }
    }

    fun onImageCaptured(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(mediaSelected = currentState.mediaSelected + uri)
        }
    }

    fun onMediaRemoved(uri: Uri) {
        _uiState.update { currentState ->
            currentState.copy(mediaSelected = currentState.mediaSelected - uri)
        }
    }

    fun onCreateNewInterestPoint(name: String, iconCode: Int) {
        viewModelScope.launch {
            try {
                addInterestPointUseCase.invoke(
                    EstateInterestPoints(
                        interestPointsName = name,
                        iconCode = iconCode
                    )
                )
            } catch (e: Exception) {
                Log.e("AddEstateViewModel", "Error creating new interest point: $e")
            }
        }
    }

    fun onInterestPointSelected(selectedPoints: List<EstateInterestPoints>) {
        _uiState.update { it.copy(selectedInterestPoints = selectedPoints) }
    }

    fun removeSelectedInterestPoint(estateInterestPoints: EstateInterestPoints) {
        _uiState.update { currentState ->
            currentState.copy(selectedInterestPoints = currentState.selectedInterestPoints - estateInterestPoints)
        }
    }


    fun onSaveButtonClick() {
        _uiState.update {
            val currentState = uiState.value
            val newState = currentState.copy(
                titleError = currentState.title.isBlank(),
                typeError = currentState.type.isBlank(),
                offerError = currentState.offer.isBlank(),
                sellingPriceError = if (currentState.offer == "Sell") currentState.sellingPrice.isBlank() else false,
                rentError = if (currentState.offer == "Rent") currentState.rent.isBlank() else false,
                surfaceError = currentState.surface.isBlank(),
                nbRoomsError = currentState.nbRooms.isBlank(),
                etagesError = currentState.etages.isBlank(),
                addressError = currentState.address.isBlank(),
                zipCodeError = currentState.zipCode.isBlank(),
                cityError = currentState.city.isBlank(),
                descriptionError = currentState.description.isBlank(),
                mediaError = currentState.mediaSelected.isEmpty(),
                interestPointError = currentState.selectedInterestPoints.isEmpty()
            )
            val isAllFieldsValid = !newState.titleError && !newState.typeError && !newState.offerError &&
                    !newState.sellingPriceError && !newState.rentError && !newState.surfaceError &&
                    !newState.nbRoomsError && !newState.etagesError && !newState.addressError &&
                    !newState.zipCodeError && !newState.cityError && !newState.descriptionError &&
                    !newState.mediaError && !newState.interestPointError
            newState.copy(isSavedEnable = isAllFieldsValid)
        }
        if(uiState.value.isSavedEnable){
            saveEstate()
        }
    }

    fun saveEstate() {
        viewModelScope.launch {
            val currentState = uiState.value
            val estate = Estate(
                title = currentState.title,
                typeOfEstate = currentState.type,
                typeOfOffer = currentState.offer,
                etage = currentState.etages,
                address = currentState.address,
                zipCode = currentState.zipCode,
                city = currentState.city,
                description = currentState.description,
                sellingPrice = currentState.sellingPrice.toIntOrNull(),
                rent = currentState.rent.toIntOrNull(),
                surface = currentState.surface.toInt(),
                nbRooms = currentState.nbRooms.toInt(),
                addDate = System.currentTimeMillis(),
            )
            withContext(Dispatchers.IO) {
                try {
                    addEstateUseCase.invoke(
                        entry = estate,
                        interestPoints = currentState.selectedInterestPoints.map { it },
                        pics = currentState.mediaSelected
                    ).collect { estateId ->
                        if (estateId > 0) {
                            // Estate saved successfully
                            _uiState.update {
                                it.copy(isEstateSaved = true)
                            }
                        } else {
                            // Handle error
                        }
                    }
                } catch (e: Exception) {
                    Log.e("AddEstateViewModel", "Error saving estate: $e")
                }
            }
        }
    }

    private fun getIconForCode(iconCode: Int): ImageVector {
        return RemIcon.iconMapping[iconCode] ?: Icons.Rounded.Place
    }

    private fun getIconCodeForName(name: String): Int {
        return iconMapping.entries.firstOrNull { it.value.name == name }?.key ?: 0
    }

    fun showBlankValue() {
        //TODO
    }


}
