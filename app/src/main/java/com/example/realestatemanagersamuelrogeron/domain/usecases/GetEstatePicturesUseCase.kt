package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetEstatePicturesUseCase {
    suspend fun invoke(id : Long): Flow<List<EstatePictures>>
}
class GetEstatePicturesUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstatePicturesUseCase {
    override suspend fun invoke(id: Long): Flow<List<EstatePictures>> = flow{
        try{
            estateRepository.getEstatePictures(id).firstOrNull()
        }catch (e: Exception){
            Log.e("GetEstatePicturesUseCase", "Error getting pics of estate with id $id")
        }
    }
}