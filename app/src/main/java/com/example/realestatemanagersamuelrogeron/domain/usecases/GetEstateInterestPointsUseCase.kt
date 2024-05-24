package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetEstateInterestPointsUseCase {
    suspend fun invoke(id : Long): Flow<List<EstateInterestPoints>>
}
class GetEstateInterestPointsUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateInterestPointsUseCase {
    override suspend fun invoke(id: Long): Flow<List<EstateInterestPoints>> {
        var interestPoints = flow<List<EstateInterestPoints>> {
            emptyList<EstateInterestPoints>()
        }

        try{
            interestPoints = estateRepository.getInterestPointsByEstateId(id)
            return interestPoints
        }catch (e: Exception){
            return interestPoints
            Log.e("GetEstateMediasUseCase", "Error getting pics of estate with id $id")
        }
    }
}