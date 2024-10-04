package com.example.realestatemanagersamuelrogeron.ui.map_screen.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddLatLngToEstatesUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllEstatesWithDetailsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateWithDetailUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetUserLocationUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAllEstatesWithPicturesUseCaseImpl: GetAllEstatesWithDetailsUseCaseImpl,
    private val getCurrencyPreferenceUseCaseImpl: GetCurrencyPreferenceUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl,
    private val getEstateWithDetailUseCaseImpl: GetEstateWithDetailUseCaseImpl,
    private val getUserLocationUseCaseImpl: GetUserLocationUseCaseImpl,
) : ViewModel() {
    private val TAG = "MapViewModel"

    private val _viewState = MutableStateFlow<MapState>(MapState.Loading)
    val viewState: StateFlow<MapState> = _viewState.asStateFlow()

    private val _filter = MutableStateFlow(EstateFilter())
    val filter: StateFlow<EstateFilter> = _filter.asStateFlow()

    private val _currencyPreference = MutableStateFlow(false)


    init {
        viewModelScope.launch {
            getCurrencyPreferenceUseCaseImpl.invoke().collectLatest { isEuro ->
                _currencyPreference.value = isEuro
                loadEstatesWithDetail()
            }
        }
        addLatLngToEstates()
        loadEstatesWithDetail()
        getUserLocation()
    }


    private fun addLatLngToEstates() {
        viewModelScope.launch {
            addLatLngToEstatesUseCaseImpl.invoke()
        }
    }

    private fun loadEstatesWithDetail() {
        viewModelScope.launch {
            getAllEstatesWithPicturesUseCaseImpl.execute(filter = EstateFilter(requireLatLng = 1))
                .catch { exception ->
                    _viewState.emit(MapState.Error(exception.message ?: "Can't get the estate"))
                    Log.w(ContentValues.TAG, "loadEstatesWithPictures: ", exception)
                    // Handle exceptions, if any
                }
                .collect { estatesWithDetail ->
                    _viewState.emit(MapState.Success(
                        estates = estatesWithDetail,
                        isEuro = _currencyPreference.value
                    ))
                }
        }
    }

    fun updateSelectedEstate(estateId: Long) {
        viewModelScope.launch {
            getEstateWithDetailUseCaseImpl.invoke(estateId)
                .catch { exception ->
                    Log.e(
                        TAG,
                        "updateSelectedEstate: error while getting estate: $estateId",
                        exception
                    )
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

    fun unselectEstate() {
        viewModelScope.launch {
            val currentState = _viewState.value
            if (currentState is MapState.Success) {
                _viewState.emit(
                    MapState.Success(
                        estates = currentState.estates,
                        estateSelected = null
                    )
                )
            }
        }
    }

    private fun getUserLocation() {
        viewModelScope.launch {
            val currentState = _viewState.value
            if (currentState is MapState.Success) {
                getUserLocationUseCaseImpl.invoke()
                    .catch { exception ->
                        Log.e(
                            TAG,
                            "getUserLocation: error while getting user location",
                            exception
                        )
                    }.collect { userLocation ->
                        _viewState.emit(
                            MapState.Success(
                                estates = currentState.estates,
                                estateSelected = currentState.estateSelected,
                                userLocation = userLocation,
                            )
                        )
                    }
            }
        }
    }

    fun updateFilter(newFilter: EstateFilter) {
        _filter.value = newFilter
        loadEstatesWithDetail()
    }
}