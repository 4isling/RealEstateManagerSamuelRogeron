package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UpdateEstateUseCase {
    suspend fun invoke(
        estate: Estate,
        interestPoints: List<EstateInterestPoints>,
        pictures: List<EstatePictures>
    )
}
class UpdateEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
): UpdateEstateUseCase {
    override suspend fun invoke(
        estate: Estate,
        interestPoints: List<EstateInterestPoints>,
        pictures: List<EstatePictures>
    ){
        withContext(Dispatchers.IO){

        }
    }
}
