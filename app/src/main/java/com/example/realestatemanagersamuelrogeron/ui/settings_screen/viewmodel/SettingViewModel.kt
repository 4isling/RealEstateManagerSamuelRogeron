package com.example.realestatemanagersamuelrogeron.ui.settings_screen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.DeleteInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.SaveCurrencyPreferenceUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val addInterestPointUseCaseImpl: AddInterestPointUseCaseImpl,
    private val deleteInterestPointUseCaseImpl: DeleteInterestPointUseCaseImpl,
    private val getAllInterestPointUseCaseImpl: GetAllInterestPointUseCaseImpl,
    private val getCurrencyPreferenceUseCaseImpl: GetCurrencyPreferenceUseCaseImpl,
    private val saveCurrencyPreferenceUseCaseImpl: SaveCurrencyPreferenceUseCaseImpl,
) : ViewModel() {
    private val _viewState = MutableStateFlow<SettingViewState>(SettingViewState.Loading)
    val viewState: StateFlow<SettingViewState> = _viewState.asStateFlow()

    init {
        loadInterestPoints()
    }

    private fun loadInterestPoints() {
        viewModelScope.launch {
            try {
                val interestPointsList = getAllInterestPointUseCaseImpl.invoke().first()
                val selectedCurrency = getCurrencyPreferenceUseCaseImpl.invoke().first()
                _viewState.emit(
                    SettingViewState.Success(
                        interestPoints = interestPointsList,
                        selectedCurrency = selectedCurrency
                    )
                )
            } catch (exception: Exception) {
                _viewState.emit(SettingViewState.Error(exception.message ?: "Error"))
            }
        }
    }

    fun onInterestPointDeleted(interestPoint: EstateInterestPoints) {
        viewModelScope.launch {
            deleteInterestPointUseCaseImpl.invoke(interestPoint)
        }
    }

    fun onInterestPointAdded(name: String, iconCode: Int) {
        viewModelScope.launch {
            try {
                addInterestPointUseCaseImpl.invoke(
                    EstateInterestPoints(
                        interestPointsName = name,
                        iconCode = iconCode
                    )
                )
            } catch (e: Exception) {
                Log.e("SettingViewModel", "Error while adding interestPoint: $e")
            }
        }
    }

    fun onCurrencyChange(isEuro: Boolean) {
        viewModelScope.launch {
            saveCurrencyPreferenceUseCaseImpl.invoke(isEuro)
            if (viewState.value is SettingViewState.Success) {
                val currentState = viewState.value as SettingViewState.Success
                _viewState.emit(
                    currentState.copy(selectedCurrency = isEuro)
                )
            }
        }
    }
}