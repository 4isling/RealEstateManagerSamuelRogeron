package com.example.realestatemanagersamuelrogeron.ui.settings_screen.viewmodel

import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints

sealed class SettingViewState {

    object Loading: SettingViewState()

    data class Success(
        val interestPoints: List<EstateInterestPoints>,
        val selectedCurrency: Boolean,
    ): SettingViewState()

    data class Error(val exception: String): SettingViewState()
}