package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.utils.DataStorePreference
import javax.inject.Inject

interface SaveCurrencyPreferenceUseCase {
    suspend fun invoke(
        isEuro: Boolean
    )
}

class SaveCurrencyPreferenceUseCaseImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
): SaveCurrencyPreferenceUseCase {
    override suspend fun invoke(isEuro: Boolean) {
        dataStorePreference.save(DataStorePreference.PreferencesKeys.display_euro.name, isEuro)
    }
}