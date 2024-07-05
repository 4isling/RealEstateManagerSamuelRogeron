package com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithPicturesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateWithDetailUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAllEstatesWithPicturesUseCaseImpl: GetAllEstatesWithPicturesUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl,
    private val getEstateWithDetailUseCaseImpl: GetEstateWithDetailUseCaseImpl,
) : ViewModel() {
    private val TAG = "MapViewModel"

    private val _viewState = MutableStateFlow<MapState>(MapState.Loading)
    val viewState: StateFlow<MapState> = _viewState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _canLoadMoreItems = MutableStateFlow(true)
    val canLoadMoreItems: StateFlow<Boolean> = _canLoadMoreItems.asStateFlow()

    private val _filter = MutableStateFlow(EstateFilter())
    val filter: StateFlow<EstateFilter> = _filter.asStateFlow()
    init {
        addLatLngToEstates()
        loadEstatesWithDetail()
    }

    private fun addLatLngToEstates(){
        viewModelScope.launch {
            addLatLngToEstatesUseCaseImpl.invoke()
        }
    }

    private fun loadEstatesWithDetail() {
        viewModelScope.launch {
            getAllEstatesWithPicturesUseCaseImpl.execute(filter = EstateFilter(requireLatLng = 1))
                .catch { exception ->
                    _viewState.emit(MapState.Error(exception.message ?: "Can't get the estate"))
                    _isLoading.emit(false)
                    Log.w(ContentValues.TAG, "loadEstatesWithPictures: ", exception)
                    // Handle exceptions, if any
                }
                .collect { estatesWithDetail ->
                    _viewState.emit(MapState.Success(estates = estatesWithDetail))
                }
        }
    }

    fun updateSelectedEstate(estateId: Long){
        viewModelScope.launch {
            getEstateWithDetailUseCaseImpl.invoke(estateId)
                .catch { exception ->
                    Log.e(TAG, "updateSelectedEstate: error while getting estate: $estateId", exception)
                }
                .collect { estateWithDetails ->
                    val currentState = _viewState.value
                    if (currentState is MapState.Success) {
                        _viewState.emit(
                            MapState.Success(
                                estates = currentState.estates,
                                estateSelected = estateWithDetails
                            )
                        )
                    }
                }
        }
    }

    fun updateFilter(newFilter: EstateFilter){
        _filter.value = newFilter
        loadEstatesWithDetail()
    }
}