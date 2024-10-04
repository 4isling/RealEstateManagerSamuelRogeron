package com.example.realestatemanagersamuelrogeron.ui.edit_screen.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.DeleteEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.EditEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateWithDetailUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.UriPermissionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class EditEstateViewModel @Inject constructor(
    private val getEstateWithDetailUseCase: GetEstateWithDetailUseCaseImpl,
    private val editEstateUseCase: EditEstateUseCase,
    private val getCurrencyPreferenceUseCase: GetCurrencyPreferenceUseCase,
    private val addInterestPointUseCase: AddInterestPointUseCase,
    private val getAllInterestPointUseCase: GetAllInterestPointUseCase,
    private val uriPermissionHelper: UriPermissionHelper,
    private val deleteEstateUseCaseImpl: DeleteEstateUseCaseImpl,
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState = MutableStateFlow(EditEstateState())
    val uiState: StateFlow<EditEstateState> = _uiState.asStateFlow()
    private val contentResolver: ContentResolver = context.contentResolver


    init {
        viewModelScope.launch {
            getCurrencyPreferenceUseCase.invoke().collect { isEuro ->
                _uiState.update { it.copy(isEuro = isEuro) }
            }
        }
        val estateId = savedStateHandle.get<Long>("estateId")
        loadEstate(estateId = estateId)
        loadInterestPoints()
    }
    
    private fun loadEstate(estateId: Long?) {
        if (estateId != null) {
            viewModelScope.launch {
                try {
                    getEstateWithDetailUseCase.invoke(estateId).collect { estateWithDetails ->
                        _uiState.update { it.copy(
                            estateWithDetails = estateWithDetails,
                            displayPrice = formatPrice(estateWithDetails.estate.price, it.isEuro)
                        ) }
                    }
                } catch (e: Exception) {
                    when (e) {
                        is CancellationException -> Log.d("EditViewModel", "loadEstate: Coroutine was cancelled")
                        else -> {
                            Log.e("EditViewModel", "loadEstate error: ", e)
                            _uiState.update { it.copy(error = "Failed to load estate: ${e.message}") }
                        }
                    }
                }
            }
        }
    }

    private fun loadInterestPoints() {
        viewModelScope.launch {
            try {
                getAllInterestPointUseCase.invoke().collect { interestPoints ->
                    _uiState.update { it.copy(allInterestPoints = interestPoints) }
                }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> Log.d("EditViewModel", "loadInterestPoints: Coroutine was cancelled")
                    else -> {
                        Log.e("EditViewModel", "loadInterestPoints error: ", e)
                        _uiState.update { it.copy(error = "Failed to load interest points: ${e.message}") }
                    }
                }
            }
        }
    }

    fun onCreateNewInterestPoint(name: String, iconCode: Int) {
        viewModelScope.launch {
            val newInterestPoint = EstateInterestPoints(interestPointsName = name, iconCode = iconCode)
            val id = addInterestPointUseCase.invoke(newInterestPoint)
            if (id > 0) {
                loadInterestPoints()
            }
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

    fun removeSelectedInterestPoint(estateInterestPoints: EstateInterestPoints) {
        _uiState.update { currentState ->
            val updatedInterestPoints = currentState.estateWithDetails.estateInterestPoints - estateInterestPoints
            val updatedEstateWithDetails = currentState.estateWithDetails.copy(
                estateInterestPoints = updatedInterestPoints
            )
            currentState.copy(estateWithDetails = updatedEstateWithDetails)
        }
    }

    fun updateEstateProperty(property: String, value: Any) {
        _uiState.update { currentState ->
            val updatedEstate = when (property) {
                "title" -> currentState.estateWithDetails.estate.copy(title = value as String)
                "typeOfEstate" -> currentState.estateWithDetails.estate.copy(typeOfEstate = value as String)
                "typeOfOffer" -> currentState.estateWithDetails.estate.copy(typeOfOffer = value as String)
                "description" -> currentState.estateWithDetails.estate.copy(description = value as String)
                "etage" -> currentState.estateWithDetails.estate.copy(etage = value as String)
                "address" -> currentState.estateWithDetails.estate.copy(address = value as String)
                "city" -> currentState.estateWithDetails.estate.copy(city = value as String)
                "zipCode" -> currentState.estateWithDetails.estate.copy(zipCode = value as String)
                "region" -> currentState.estateWithDetails.estate.copy(region = value as String)
                "country" -> currentState.estateWithDetails.estate.copy(country = value as String)
                "price" -> currentState.estateWithDetails.estate.copy(price = (value as String).toIntOrNull() ?: 0)
                "surface" -> currentState.estateWithDetails.estate.copy(surface = (value as String).toIntOrNull() ?: 0)
                "nbRooms" -> currentState.estateWithDetails.estate.copy(nbRooms = (value as String).toIntOrNull() ?: 0)
                else -> currentState.estateWithDetails.estate
            }
            val updatedEstateWithDetails = currentState.estateWithDetails.copy(estate = updatedEstate)
            currentState.copy(
                estateWithDetails = updatedEstateWithDetails,
                displayPrice = formatPrice(updatedEstate.price, currentState.isEuro)
            )
        }
    }

    fun onDeleteEstate(){
        viewModelScope.launch {
            deleteEstateUseCaseImpl.invoke(uiState.value.estateWithDetails.estate.estateId)
        }
    }

    fun onSaveButtonClick() {
        viewModelScope.launch {
            try {
                val currentState = uiState.value
                if (validateFields(currentState)) {
                    val success = editEstateUseCase.updateEstateWithDetails(currentState.estateWithDetails)
                    if (success) {
                        _uiState.update { it.copy(isEstateSaved = true) }
                    } else {
                        _uiState.update { it.copy(error = "Failed to save estate") }
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is CancellationException -> Log.d("EditViewModel", "saveUpdatedEstate: Coroutine was cancelled")
                    else -> {
                        Log.e("EditViewModel", "saveUpdatedEstate error: ", e)
                        _uiState.update { it.copy(error = "Error saving estate: ${e.message}") }
                    }
                }
            }
        }
    }
    private fun validateFields(state: EditEstateState): Boolean {
        val errors = mutableMapOf<String, Boolean>()
        errors["title"] = state.estateWithDetails.estate.title.isBlank()
        errors["type"] = state.estateWithDetails.estate.typeOfEstate.isBlank()
        errors["offer"] = state.estateWithDetails.estate.typeOfOffer.isBlank()
        errors["price"] = state.estateWithDetails.estate.price <= 0
        errors["surface"] = state.estateWithDetails.estate.surface <= 0
        errors["nbRooms"] = state.estateWithDetails.estate.nbRooms <= 0
        errors["etages"] = state.estateWithDetails.estate.etage.isBlank()
        errors["address"] = state.estateWithDetails.estate.address.isBlank()
        errors["zipCode"] = state.estateWithDetails.estate.zipCode.isBlank()
        errors["city"] = state.estateWithDetails.estate.city.isBlank()
        errors["region"] = state.estateWithDetails.estate.region.isBlank()
        errors["country"] = state.estateWithDetails.estate.country.isBlank()
        errors["description"] = state.estateWithDetails.estate.description.isBlank()
        errors["media"] = state.estateWithDetails.estatePictures.isEmpty()
        errors["interestPoint"] = state.estateWithDetails.estateInterestPoints.isEmpty()

        _uiState.update { it.copy(
            titleError = errors["title"] ?: false,
            typeError = errors["type"] ?: false,
            offerError = errors["offer"] ?: false,
            priceError = errors["price"] ?: false,
            surfaceError = errors["surface"] ?: false,
            nbRoomsError = errors["nbRooms"] ?: false,
            etagesError = errors["etages"] ?: false,
            addressError = errors["address"] ?: false,
            zipCodeError = errors["zipCode"] ?: false,
            cityError = errors["city"] ?: false,
            regionError = errors["region"] ?: false,
            countryError = errors["country"] ?: false,
            descriptionError = errors["description"] ?: false,
            mediaError = errors["media"] ?: false,
            interestPointError = errors["interestPoint"] ?: false,
            isSaveEnabled = !errors.values.any { it }
        ) }

        return !errors.values.any { it }
    }

    private fun saveUpdatedEstate() {
        viewModelScope.launch {

            val currentState = uiState.value
            try {
                val success = editEstateUseCase.updateEstateWithDetails(currentState.estateWithDetails)
                if (success) {
                    _uiState.update { it.copy(isEstateSaved = true) }
                } else {
                    _uiState.update { it.copy(error = "Failed to save estate") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = "Error saving estate: ${e.message}") }
            }
        }
    }

    private fun formatPrice(price: Int, isEuro: Boolean): String {
        return if (isEuro) {
            val euroPrice = (price * 0.92).toInt() // Assuming 1 USD = 0.92 EUR
            "$euroPrice €"
        } else {
            "$price $"
        }
    }

    private suspend fun getMimeType(uri: Uri): String = withContext(Dispatchers.IO) {
        contentResolver.getType(uri) ?: run {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase(Locale.ROOT)) ?: "application/octet-stream"
        }
    }

    private suspend fun generateUniqueName(uri: Uri): String {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val mimeType = getMimeType(uri)
        val prefix = when {
            mimeType.startsWith("image/") -> "IMG"
            mimeType.startsWith("video/") -> "VID"
            else -> "FILE"
        }
        val fileExtension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: run {
            MimeTypeMap.getFileExtensionFromUrl(uri.toString()) ?: when {
                mimeType.startsWith("image/") -> "jpg"
                mimeType.startsWith("video/") -> "mp4"
                else -> "bin"
            }
        }
        return "${prefix}_${timestamp}.${fileExtension}"
    }

    fun onMediaPicked(medias: List<Uri>) {
        viewModelScope.launch {
            medias.forEach { uriPermissionHelper.takePersistableUriPermission(it) }
            _uiState.update { currentState ->
                val newPictures = medias.map { uri ->
                    EstateMedia(
                        estateId = currentState.estateWithDetails.estate.estateId,
                        uri = uri.toString(),
                        mimeType = getMimeType(uri),
                        name = generateUniqueName(uri)
                    )
                }
                val updatedEstateWithDetails = currentState.estateWithDetails.copy(
                    estatePictures = currentState.estateWithDetails.estatePictures + newPictures
                )
                currentState.copy(estateWithDetails = updatedEstateWithDetails)
            }
        }
    }

    fun onImageCaptured(uri: Uri) {
        viewModelScope.launch {
            uriPermissionHelper.takePersistableUriPermission(uri)
            val mimeType = getMimeType(uri)
            val uniqueName = generateUniqueName(uri)

            _uiState.update { currentState ->
                val newPicture = EstateMedia(
                    estateId = currentState.estateWithDetails.estate.estateId,
                    uri = uri.toString(),
                    mimeType = mimeType,
                    name = uniqueName
                )
                val updatedEstateWithDetails = currentState.estateWithDetails.copy(
                    estatePictures = currentState.estateWithDetails.estatePictures + newPicture
                )
                currentState.copy(estateWithDetails = updatedEstateWithDetails)
            }
        }
    }

    fun onMediaRemoved(uri: Uri) {
        _uiState.update { currentState ->
            val updatedPictures = currentState.estateWithDetails.estatePictures.filter { it.uri != uri.toString() }
            val updatedEstateWithDetails = currentState.estateWithDetails.copy(estatePictures = updatedPictures)
            currentState.copy(estateWithDetails = updatedEstateWithDetails)
        }
    }
}

data class EditEstateState(
    val estateWithDetails: EstateWithDetails = EstateWithDetails(
        estate = Estate(
            0L, "", "", "", "", "", "", "", "", "", "",
            0, 0, "", 0, 0, 0, true, false
        ),
        estatePictures = emptyList(),
        estateInterestPoints = emptyList()
    ),
    val allInterestPoints: List<EstateInterestPoints> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEuro: Boolean = false,
    val displayPrice: String = "",
    val titleError: Boolean = false,
    val typeError: Boolean = false,
    val offerError: Boolean = false,
    val priceError: Boolean = false,
    val surfaceError: Boolean = false,
    val nbRoomsError: Boolean = false,
    val etagesError: Boolean = false,
    val addressError: Boolean = false,
    val zipCodeError: Boolean = false,
    val cityError: Boolean = false,
    val regionError: Boolean = false,
    val countryError: Boolean = false,
    val descriptionError: Boolean = false,
    val mediaError: Boolean = false,
    val interestPointError: Boolean = false,
    val isSaveEnabled: Boolean = false,
    val isEstateSaved: Boolean = false,
)