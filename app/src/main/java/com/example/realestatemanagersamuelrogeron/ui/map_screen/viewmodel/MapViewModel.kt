package com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithPicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.SortType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAllEstatesWithPicturesUseCaseImpl: GetAllEstatesWithPicturesUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl
) : ViewModel() {
    init {
        //addLatLngToEstatesUseCaseImpl.invoke()
        loadEstatesWithPictures()
    }
    private val _viewState = MutableStateFlow<MapState>(MapState.Loading)

    private fun addLatLngToEstates(){

    }

    private fun loadEstatesWithPictures() {
        viewModelScope.launch {
            getAllEstatesWithPicturesUseCaseImpl.execute(SortType.Default)
                .catch { exception ->
                    Log.w(ContentValues.TAG, "loadEstatesWithPictures: ", exception)
                    // Handle exceptions, if any
                }
                .collect { estatesWithPictures ->
                    _viewState.emit(MapState.Success(estatesWithPictures))
                }
        }
    }

}