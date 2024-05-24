package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetEstateMediasUseCase {
    suspend fun invoke(id : Long): Flow<List<EstateMedia>>
}
class GetEstateMediasUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateMediasUseCase {
    override suspend fun invoke(id: Long): Flow<List<EstateMedia>> {
        var pics = flow<List<EstateMedia>> {
            emptyList<EstateMedia>()
        }
        try{
            pics = estateRepository.getEstatePictures(id)
            return pics
        }catch (e: Exception){
            return pics
            Log.e("GetEstateMediasUseCase", "Error getting pics of estate with id $id")
        }
    }
}