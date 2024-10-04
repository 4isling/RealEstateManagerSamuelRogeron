package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.utils.FileTypeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface AddEstateUseCase {
    suspend fun invoke(
        entry: Estate,
        interestPoints: List<EstateInterestPoints>,
        pics: List<Uri>
    ): Flow<Long>
}

class AddEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
    private val context: Context
) : AddEstateUseCase {
    override suspend fun invoke(
        entry: Estate,
        interestPoints: List<EstateInterestPoints>,
        pics: List<Uri>
    ): Flow<Long> = flow {
        try {
            val estateId = estateRepository.addEstate(entry)
            Log.i("AddEstateUseCase", "estateId: $estateId")

            interestPoints.forEach { point ->
                estateRepository.addEstateInterestPointCrossRef(
                    EstateInterestPointCrossRef(
                        estateId = estateId,
                        estateInterestPointId = point.estateInterestPointId
                    )
                )
            }

            pics.forEachIndexed { index, pic ->
                val mimeType = FileTypeHelper.getMimeType(context = context, uri = pic)
                val estatePicture = EstateMedia(
                    estateId = estateId,
                    uri = pic.toString(),
                    mimeType = mimeType,
                    name = "e${estateId}p${index + 1}"
                )
                estateRepository.addEstatePicture(estatePicture)
            }

            emit(estateId) // Emit the estate ID as the final result
        } catch (e: Exception) {
            Log.e("AddEstateUseCase", "Error adding estate: $e")
            emit(-1L) // Emit an error indicator
        }
    }.flowOn(Dispatchers.IO)
}