package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import java.lang.RuntimeException
import javax.inject.Inject

interface AddInterestPointsToEstateUseCase {
    suspend fun invoke(
        estateId: Long,
        interestPoints: List<String>
    ): Result<Unit>
}

class AddInterestPointsToEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
): AddInterestPointsToEstateUseCase {
    override suspend fun invoke(
        estateId: Long,
        interestPoints: List<String>
    ): Result<Unit> {
       return try {
           val listInterestPoints = emptyList<EstateInterestPoints>()
           for (string in interestPoints){
               val estateInterestPoint = EstateInterestPoints(estateId = estateId, interestPointsName = string)
               listInterestPoints.plus(estateInterestPoint)
           }
           estateRepository.addEstateInterestPoints(listInterestPoints)
           Result.success(Unit)

       }catch(exception: Exception){
           Result.failure(AddInterestPointException("failed to add interest point to estate with id: $estateId", exception))
       }
    }
    class AddInterestPointException(message: String, cause: Throwable): RuntimeException(message, cause)
}

