package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetEstatePicturesUseCase {
    suspend fun invoke(id : Long): Flow<List<EstateMedia>>
}
class GetEstatePicturesUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstatePicturesUseCase {
    override suspend fun invoke(id: Long): Flow<List<EstateMedia>> {
        var pics = flow<List<EstateMedia>> {
            emptyList<EstateMedia>()
        }
        try{
            pics = estateRepository.getEstatePictures(id)
            return pics
        }catch (e: Exception){
            return pics
            Log.e("GetEstatePicturesUseCase", "Error getting pics of estate with id $id")
        }
    }
}