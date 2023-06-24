package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetEstateUseCase {
    suspend fun invoke(id : Long): Flow<Estate>
}
class GetEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateUseCase {
    override suspend fun invoke(id: Long): Flow<Estate> = flow{
        try{
            estateRepository.getEstateById(id).firstOrNull()
        }catch (e: Exception){
            Log.e("GetEstateUseCase", "Error getting estate with id $id")
        }

    }
}