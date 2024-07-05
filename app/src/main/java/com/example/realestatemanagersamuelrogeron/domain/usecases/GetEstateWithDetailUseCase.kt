package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetEstateWithDetailUseCase {
    suspend fun invoke(id: Long): Flow<EstateWithDetails?>
}

class GetEstateWithDetailUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
) : GetEstateWithDetailUseCase {
    override suspend fun invoke(id: Long): Flow<EstateWithDetails> {
        var estate = flow<EstateWithDetails> {
            (
                    Estate(
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
                        0,
                        null,
                        "",
                        0,
                        0,
                        0,
                        true,
                        false
                    )
                    )
        }
        try {
            estate = estateRepository.getEstateWithDetailById(id)
            return estate
        } catch (e: Exception) {
            Log.e("GetEstateUseCase", "Error getting estate with id $id")
            return estate
        }

    }
}