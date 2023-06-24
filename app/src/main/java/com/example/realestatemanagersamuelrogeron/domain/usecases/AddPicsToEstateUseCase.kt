package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.net.Uri
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import java.lang.RuntimeException
import javax.inject.Inject

interface AddPicsToEstateUseCase {
    suspend fun invoke(
        id: Long,
        pics: List<Uri>
    ): Result<Unit>
}

class AddPicsToEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
    ): AddPicsToEstateUseCase {
    override suspend fun invoke(
        id: Long,
        pics: List<Uri>
    ):Result<Unit> {
        return try {
            var uri = ""
            var i=0
            val listEstatePictures = emptyList<EstatePictures>()
                for(pic in pics) {
                    uri = pic.toString()
                    i++
                    val estatePicture = EstatePictures(estateId = id, pictureUri = uri, name = ("e" + id.toString() + "p" + i.toString()))
                    listEstatePictures.plus(estatePicture)
                }
            estateRepository.addEstatePictures(listEstatePictures)
            Result.success(Unit)

        }catch (exception : Exception){
             Result.failure(AddPicturesException("Failed to add pics to estate with id: $id", exception))
        }
    }

    class AddPicturesException(message: String, cause: Throwable): RuntimeException(message, cause)
}
