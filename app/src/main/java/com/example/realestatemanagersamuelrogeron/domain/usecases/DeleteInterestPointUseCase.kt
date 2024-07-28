package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import javax.inject.Inject

interface DeleteInterestPointUseCase {
    suspend fun invoke(interestPoint: EstateInterestPoints)
}

class DeleteInterestPointUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : DeleteInterestPointUseCase {
    override suspend fun invoke(interestPoint: EstateInterestPoints) {
        try {
            estateRepository.delete(interestPoint)
        }catch (e: Exception){
            Log.e("DeleteInterestPointUseCase", "Error deleteInterestPoint: $e ")
        }
    }
}