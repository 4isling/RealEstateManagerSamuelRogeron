package com.example.realestatemanagersamuelrogeron.ui.detail_screen.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.state.DetailState
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateInterestPointsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstatePicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.UpdateEstateUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EstateDetailViewModel @Inject constructor(
    private val getEstateUseCaseImpl: GetEstateUseCaseImpl,
    private val getEstatePicturesUseCaseImpl: GetEstatePicturesUseCaseImpl,
    private val getEstateInterestPointsUseCaseImpl: GetEstateInterestPointsUseCaseImpl,
    private val updateEstateUseCaseImpl: UpdateEstateUseCaseImpl,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _uiState = MutableStateFlow(DetailState())
    val uiState: StateFlow<DetailState> = _uiState.asStateFlow()

    private val _estate = MutableStateFlow<Estate>( Estate(
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
        "",
        0,
        0,
        0,
        0,
        true,
        false)
    )
    private val _estateMedia = MutableStateFlow<List<EstateMedia>>(emptyList<EstateMedia>())
    private val _estateInterestPoints = MutableStateFlow<List<EstateInterestPoints>>(emptyList<EstateInterestPoints>())
    private val _estateIsFav = derivedStateOf {
        _estate.value.isFav
    }
    val estate: MutableStateFlow<Estate> get() = _estate
    val estateMedia: MutableStateFlow<List<EstateMedia>> get() = _estateMedia
    val estateInterestPoints: MutableStateFlow<List<EstateInterestPoints>> get() = _estateInterestPoints

    init {
        val estateId = savedStateHandle.get<Long>("estateId")
        println("estateId: "+ estateId)

        if (estateId != null) {
            viewModelScope.launch{
                getEstateUseCaseImpl.invoke(estateId).collect { estate ->
                    _estate.value = estate
                }
                getEstateInterestPointsUseCaseImpl.invoke(estateId).collect{
                    interestPoints ->
                    if(interestPoints.isNotEmpty()){
                        _estateInterestPoints.value = interestPoints
                    }else{
                        Log.e(TAG, "interestPoint: null interestPoints", )
                    }

                }
                getEstatePicturesUseCaseImpl.invoke(estateId).collect{
                    pics ->
                    if(pics.isNotEmpty()){
                        _estateMedia.value = pics
                        Log.i(TAG, "estatePicturesVal: ${_estateMedia.value} ")
                    }else{
                        Log.e(TAG,"pic: null")
                    }
                }
            }
        }
    }

    fun onEditButtonClick(){

    }
}