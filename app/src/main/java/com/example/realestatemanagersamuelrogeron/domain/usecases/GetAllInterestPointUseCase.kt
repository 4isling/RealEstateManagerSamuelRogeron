package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetAllInterestPointUseCase {
    suspend fun invoke(): Flow<List<EstateInterestPoints>>
}
class GetAllInterestPointUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
): GetAllInterestPointUseCase {
    override suspend fun invoke(): Flow<List<EstateInterestPoints>> {
        var interestPoints = flow<List<EstateInterestPoints>> {
            emptyList<EstateInterestPoints>()
        }
        return try {
            interestPoints = estateRepository.getAllInterestPoints()
            interestPoints
        }catch (e: Exception){
            Log.e("GetAllInterestPointUseCase", "Error getting all interest points $e" )
            interestPoints
        }
    }
}
