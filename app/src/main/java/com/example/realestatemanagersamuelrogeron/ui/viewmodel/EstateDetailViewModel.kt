package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateInterestPointsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstatePicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.UpdateEstateUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val _estatePictures = MutableStateFlow<List<EstatePictures>>(emptyList<EstatePictures>())
    private val _estateInterestPoints = MutableStateFlow<List<EstateInterestPoints>>(emptyList<EstateInterestPoints>())
    private val _estateIsFav = derivedStateOf {
        _estate.value.isFav
    }
    val estate: MutableStateFlow<Estate> get() = _estate
    val estatePictures: MutableStateFlow<List<EstatePictures>> get() = _estatePictures
    val estateInterestPoints: MutableStateFlow<List<EstateInterestPoints>> get() = _estateInterestPoints

    var state = mutableStateOf(
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
        "",
        0,
        0,
        0,
        0)

    )


    init {
        val estateId = savedStateHandle.get<Long>("estateId")
        println("estateId: "+ estateId)
        if (estateId != null){
            viewModelScope.launch{
                getEstateUseCaseImpl.invoke(estateId).collect { estate ->
                    _estate.value = estate
                }
                getEstateInterestPointsUseCaseImpl.invoke(estateId).collect{
                    interestPoints ->
                    _estateInterestPoints.value = interestPoints
                }
                getEstatePicturesUseCaseImpl.invoke(estateId).collect{
                    pics ->
                    _estatePictures.value = pics
                }

            }
        }
    }

    fun onModifyButtonClick(){

    }
}