package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateInterestPointsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateMediasUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateUseCaseImpl
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
    private val getEstateUseCaseImpl: GetEstateUseCaseImpl,
    private val getEstatePicturesUseCaseImpl: GetEstateMediasUseCaseImpl,
    private val getEstateInterestPointsUseCaseImpl: GetEstateInterestPointsUseCaseImpl,
    private val updateEstateUseCaseImpl: UpdateEstateUseCaseImpl,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailViewState>(DetailViewState.Loading)
    val uiState: StateFlow<DetailViewState> = _uiState.asStateFlow()

    private val _estate = MutableStateFlow<Estate>(
        Estate(
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
        )
    )
    private val _estateMedia = MutableStateFlow<List<EstateMedia>>(emptyList())
    private val _estateInterestPoints = MutableStateFlow<List<EstateInterestPoints>>(emptyList())

    init {
        val estateId = savedStateHandle.get<Long>("estateId")
        println("estateId: " + estateId)

        if (estateId != null) {
            viewModelScope.launch {
                try {
                    _uiState.value = DetailViewState.Loading

                    // Fetch estate details
                    val estate = getEstateUseCaseImpl.invoke(estateId).first()
                    _estate.value = estate

                    // Fetch interest points
                    val interestPoints = getEstateInterestPointsUseCaseImpl.invoke(estateId).first()
                    _estateInterestPoints.value = interestPoints

                    // Fetch pictures
                    val pics = getEstatePicturesUseCaseImpl.invoke(estateId).first()
                    _estateMedia.value = pics

                    _uiState.value = DetailViewState.Success(
                        estate = estate,
                        medias = pics,
                        interestPoints = interestPoints
                    )

                } catch (e: Exception) {
                    _uiState.value = DetailViewState.Error(e.message ?: "Unknown error")
                }
            }
        }
    }

    fun onEditButtonClick() {
        // Implementation for edit button click
    }
}
