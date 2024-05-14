package com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
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
    private val addEstateUseCaseImpl: AddEstateUseCaseImpl,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEstateState());
    val uiState: StateFlow<AddEstateState> = _uiState.asStateFlow()

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
                "description" -> currentState.copy(description = newValue)
                "sellingPrice" -> currentState.copy(sellingPrice = newValue)
                "rent" -> currentState.copy(rent = newValue)
                "surface" -> currentState.copy(surface = newValue)
                "nbRooms" -> currentState.copy(nbRooms = newValue)
                else -> currentState
            }
        }
    }

    fun onMediaPicked(medias: List<Uri>) {
        _uiState.update {
            currentState -> currentState.copy(mediaSelected = medias)
        }
    }
    fun onImageCaptured(uri: Uri){
        _uiState.update {
            currentState -> currentState.copy(mediaSelected = currentState.mediaSelected + uri)
        }
    }

    fun onMediaRemoved(uri: Uri) {
        _uiState.update {
                currentState -> currentState.copy(mediaSelected = currentState.mediaSelected - uri)
        }
    }

    fun onInterestPointSelected(interestPoints: List<String>) {
        _uiState.update {
            currentState -> currentState.copy(interestPointsStrings = interestPoints)
        }
    }

    fun enableSave() {
        viewModelScope.launch {
            val currentState = uiState.value

        }
    }

    suspend fun saveEstate(addEstateState: AddEstateState){
        if (addEstateState.estate != null){
            withContext(Dispatchers.IO) {
                addEstateUseCaseImpl.invoke(
                    entry = addEstateState.estate,
                    interestPoints = addEstateState.interestPointsStrings,
                    pics = addEstateState.mediaSelected,
                )
            }
        }
    }
    fun showBlankValue() {
        //TODO
    }


}