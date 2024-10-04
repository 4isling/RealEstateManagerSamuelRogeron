package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.utils.DataStorePreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCurrencyPreferenceUseCase {
    suspend fun invoke(): Flow<Boolean>
}

class GetCurrencyPreferenceUseCaseImpl @Inject constructor(
    private val dataStorePreference: DataStorePreference
): GetCurrencyPreferenceUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return dataStorePreference.displayEuroBooleanFlow
    }
}