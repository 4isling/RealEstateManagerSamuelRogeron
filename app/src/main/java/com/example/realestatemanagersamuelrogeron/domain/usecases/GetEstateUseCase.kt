package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetEstateUseCase {
    suspend fun invoke(id : Long): Flow<Estate?>
}
class GetEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateUseCase {
    override suspend fun invoke(id: Long): Flow<Estate> {
        var estate = flow<Estate>{
            (Estate(
                0L,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                0,
                0,
                0,
                true,
                false))
        }
        try{
            estate = estateRepository.getEstateById(id)
            return estate
        }catch (e: Exception){
            return estate
            Log.e("GetEstateUseCase", "Error getting estate with id $id")
        }

    }
}