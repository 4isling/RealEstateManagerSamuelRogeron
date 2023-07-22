package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithPicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateListUseCaseImpl
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getEstateListUseCaseImpl: GetEstateListUseCaseImpl,
    private val getAllEstatesWithPicturesUseCaseImpl: GetAllEstatesWithPicturesUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl

) : ViewModel() {
    init {

    }


}