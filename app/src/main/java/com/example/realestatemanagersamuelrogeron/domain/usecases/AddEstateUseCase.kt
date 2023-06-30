package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.net.Uri
import android.util.Log
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AddEstateUseCase {
    suspend fun invoke(
        entry: Estate,
        interestPoints: List<String>,
        pics: List<Uri>
    ): Flow<Long>
}

class AddEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : AddEstateUseCase {
    override suspend fun invoke(
        entry: Estate,
        interestPoints: List<String>,
        pics: List<Uri>
    ): Flow<Long> = flow {
        try {
            val estateId = estateRepository.addEstate(entry)
            Log.i("AddEstateUseCase", "estateId: $estateId")
            //Interest points
            try {
                val listInterestPoints = emptyList<EstateInterestPoints>()
                for (string in interestPoints) {
                    val estateInterestPoint =
                        EstateInterestPoints(estateId = estateId, interestPointsName = string)
                    listInterestPoints.plus(estateInterestPoint)
                }
                estateRepository.addEstateInterestPoints(listInterestPoints)
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("AddEstateUseCase", "Error add estate interestPoints:$e")
            }
            //pics
            try {

                var uri = ""
                var i = 0
                val listEstatePictures = emptyList<EstatePictures>()
                for (pic in pics) {
                    uri = pic.toString()
                    i++
                    val estatePicture = EstatePictures(
                        estateId = estateId,
                        pictureUri = uri,
                        name = ("e" + estateId.toString() + "p" + i.toString())
                    )
                    listEstatePictures.plus(estatePicture)
                }
                estateRepository.addEstatePictures(listEstatePictures)
                Result.success(Unit)
            } catch (e: Exception) {
                Log.e("AddEstateUseCase", "Error add estate pictures:$e")
            }


        } catch (e: Exception) {
            Log.e("AddEstateUseCase", "Error add estate:$e")
        }

    }
}