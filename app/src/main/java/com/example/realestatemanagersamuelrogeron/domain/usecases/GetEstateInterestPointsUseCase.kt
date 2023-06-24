package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetEstateInterestPointsUseCase {
    suspend fun invoke(id : Long): Flow<List<EstateInterestPoints>>
}
class GetEstateInterestPointsUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateInterestPointsUseCase {
    override suspend fun invoke(id: Long): Flow<List<EstateInterestPoints>> = flow{
        try{
            estateRepository.getEstateInterestPoints(id).firstOrNull()
        }catch (e: Exception){
            Log.e("GetEstatePicturesUseCase", "Error getting pics of estate with id $id")
        }
    }
}