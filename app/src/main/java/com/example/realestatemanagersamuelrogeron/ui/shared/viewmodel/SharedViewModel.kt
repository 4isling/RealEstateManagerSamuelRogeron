package com.example.realestatemanagersamuelrogeron.ui.shared.viewmodel

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
class SharedEstateViewModel @Inject constructor(
    private val getAllEstatesWithDetailsUseCaseImpl: GetAllEstatesWithDetailsUseCaseImpl,
    private val getCurrencyPreferenceUseCaseImpl: GetCurrencyPreferenceUseCaseImpl,
    private val addLatLngToEstatesUseCaseImpl: AddLatLngToEstatesUseCaseImpl,
    private val getEstateWithDetailUseCaseImpl: GetEstateWithDetailUseCaseImpl,
    private val getUserLocationUseCaseImpl: GetUserLocationUseCaseImpl,
) : ViewModel() {

    private val TAG = "SharedEstateViewModel"

    private val _viewState = MutableStateFlow<SharedEstateViewState>(SharedEstateViewState.Loading)
    val viewState: StateFlow<SharedEstateViewState> = _viewState.asStateFlow()

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
            getAllEstatesWithDetailsUseCaseImpl.execute(filter = _filter.value.copy(requireLatLng = 1))
                .catch { exception ->
                    _viewState.emit(SharedEstateViewState.Error(exception.message ?: "Can't get the estates"))
                    Log.e(TAG, "loadEstatesWithDetail: ", exception)
                }
                .collect { estatesWithDetail ->
                    val currentState = _viewState.value
                    _viewState.emit(SharedEstateViewState.Success(
                        estates = estatesWithDetail,
                        estateSelected = (currentState as? SharedEstateViewState.Success)?.estateSelected,
                        userLocation = (currentState as? SharedEstateViewState.Success)?.userLocation,
                        isEuro = _currencyPreference.value,
                        estateFilter = _filter.value
                    ))
                }
        }
    }

    fun updateSelectedEstate(estateId: Long) {
        viewModelScope.launch {
            getEstateWithDetailUseCaseImpl.invoke(estateId)
                .catch { exception ->
                    Log.e(TAG, "updateSelectedEstate: error while getting estate: $estateId", exception)
                }
                .collect { estateWithDetails ->
                    val currentState = _viewState.value
                    if (currentState is SharedEstateViewState.Success) {
                        _viewState.emit(
                            currentState.copy(estateSelected = estateWithDetails)
                        )
                    }
                }
        }
    }

    fun unselectEstate() {
        viewModelScope.launch {
            val currentState = _viewState.value
            if (currentState is SharedEstateViewState.Success) {
                _viewState.emit(
                    currentState.copy(estateSelected = null)
                )
            }
        }
    }

    private fun getUserLocation() {
        viewModelScope.launch {
            getUserLocationUseCaseImpl.invoke()
                .catch { exception ->
                    Log.e(TAG, "getUserLocation: error while getting user location", exception)
                }.collect { userLocation ->
                    val currentState = _viewState.value
                    if (currentState is SharedEstateViewState.Success) {
                        _viewState.emit(
                            currentState.copy(userLocation = userLocation)
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