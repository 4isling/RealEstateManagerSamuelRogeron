package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.net.Uri
import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AddEstateUseCase {
    suspend fun invoke(
        entry: Estate,
        interestPoints: List<EstateInterestPoints>,
        pics: List<Uri>
    ): Flow<Long>
}

class AddEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : AddEstateUseCase {
    override suspend fun invoke(
        entry: Estate,
        interestPoints: List<EstateInterestPoints>,
        pics: List<Uri>
    ): Flow<Long> = flow {
        try {
            val estateId = estateRepository.addEstate(entry)
            Log.i("AddEstateUseCase", "estateId: $estateId")

            try {
                interestPoints.forEach { point ->
                    estateRepository.addEstateInterestPointCrossRef(
                        EstateInterestPointCrossRef(
                            estateId = estateId,
                            estateInterestPointId = point.estateInterestPointId
                        )
                    )
                }
            } catch (e: Exception) {
                Log.e("AddEstateUseCase", "Error adding estate interest point cross-references: $e")
                throw e
            }
            try {
                pics.forEachIndexed { index, pic ->
                    val estatePicture = EstateMedia(
                        estateId = estateId,
                        uri = pic.toString(),
                        name = "e${estateId}p${index + 1}"
                    )
                    estateRepository.addEstatePicture(estatePicture)
                }
            } catch (e: Exception) {
                Log.e("AddEstateUseCase", "Error adding estate pictures: $e")
                throw e // Re-throw to propagate error
            }

            emit(estateId) // Emit the estate ID as the final result
        } catch (e: Exception) {
            Log.e("AddEstateUseCase", "Error adding estate: $e")
            emit(-1) // Emit an error indicator (could also use Result.failure)
        }
    }
}
