package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

interface EditEstateUseCase {
    suspend fun updateEstateWithDetails(estateWithDetails: EstateWithDetails): Boolean
    suspend fun updateEstateProperties(estateId: Long, updatedProperties: Map<String, Any>): Boolean
    suspend fun addInterestPoint(estateId: Long, interestPointId: Long): Boolean
    suspend fun removeInterestPoint(estateId: Long, interestPointId: Long): Boolean
    suspend fun addEstateMedia(estateId: Long, uri: Uri): Boolean
    suspend fun removeEstateMedia(estateId: Long, mediaId: Long): Boolean
    suspend fun getEstateDetails(estateId: Long): Flow<EstateWithDetails>
    suspend fun getInterestPoints(estateId: Long): Flow<List<EstateInterestPoints>>
    suspend fun getEstateMedia(estateId: Long): Flow<List<EstateMedia>>
}

class EditEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository,
    private val context: Context
) : EditEstateUseCase {

    override suspend fun updateEstateWithDetails(estateWithDetails: EstateWithDetails): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Update the main estate information
                estateRepository.updateEstate(estateWithDetails.estate)

                // Update interest points
                val currentInterestPoints = estateRepository.getInterestPointsByEstateId(estateWithDetails.estate.estateId).first()
                val newInterestPoints = estateWithDetails.estateInterestPoints

                // Remove old interest points
                currentInterestPoints.forEach { interestPoint ->
                    if (interestPoint !in newInterestPoints) {
                        removeInterestPoint(estateWithDetails.estate.estateId, interestPoint.estateInterestPointId)
                    }
                }

                // Add new interest points
                newInterestPoints.forEach { interestPoint ->
                    if (interestPoint !in currentInterestPoints) {
                        addInterestPoint(estateWithDetails.estate.estateId, interestPoint.estateInterestPointId)
                    }
                }

                // Update estate media
                val currentMedia = estateRepository.getEstatePictures(estateWithDetails.estate.estateId).first()
                val newMedia = estateWithDetails.estatePictures

                // Remove old media
                currentMedia.forEach { media ->
                    if (media !in newMedia) {
                        removeEstateMedia(estateWithDetails.estate.estateId, media.id)
                    }
                }

                // Add new media
                newMedia.forEach { media ->
                    if (media !in currentMedia) {
                        estateRepository.addEstatePicture(media)
                    }
                }

                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error updating estate with details: $e")
                false
            }
        }
    }

    override suspend fun updateEstateProperties(estateId: Long, updatedProperties: Map<String, Any>): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val currentEstate = estateRepository.getEstateById(estateId).first()
                val updatedEstate = currentEstate.copy(
                    title = updatedProperties["title"] as? String ?: currentEstate.title,
                    typeOfEstate = updatedProperties["typeOfEstate"] as? String ?: currentEstate.typeOfEstate,
                    typeOfOffer = updatedProperties["typeOfOffer"] as? String ?: currentEstate.typeOfOffer,
                    etage = updatedProperties["etage"] as? String ?: currentEstate.etage,
                    address = updatedProperties["address"] as? String ?: currentEstate.address,
                    zipCode = updatedProperties["zipCode"] as? String ?: currentEstate.zipCode,
                    city = updatedProperties["city"] as? String ?: currentEstate.city,
                    region = updatedProperties["region"] as? String ?: currentEstate.region,
                    country = updatedProperties["country"] as? String ?: currentEstate.country,
                    description = updatedProperties["description"] as? String ?: currentEstate.description,
                    price = updatedProperties["price"] as? Int ?: currentEstate.price,
                    surface = updatedProperties["surface"] as? Int ?: currentEstate.surface,
                    nbRooms = updatedProperties["nbRooms"] as? Int ?: currentEstate.nbRooms,
                    status = updatedProperties["status"] as? Boolean ?: currentEstate.status,
                    isFav = updatedProperties["isFav"] as? Boolean ?: currentEstate.isFav
                )
                estateRepository.updateEstate(updatedEstate)
                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error updating estate properties: $e")
                false
            }
        }
    }

    override suspend fun addInterestPoint(estateId: Long, interestPointId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                estateRepository.addEstateInterestPointCrossRef(
                    EstateInterestPointCrossRef(estateId, interestPointId)
                )
                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error adding interest point: $e")
                false
            }
        }
    }

    override suspend fun removeInterestPoint(estateId: Long, interestPointId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                estateRepository.deleteInterestPointCrossRef(
                    EstateInterestPointCrossRef(estateId, interestPointId)
                )
                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error removing interest point: $e")
                false
            }
        }
    }

    override suspend fun addEstateMedia(estateId: Long, uri: Uri): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val mimeType = getMimeType(uri)
                val newMedia = EstateMedia(
                    estateId = estateId,
                    uri = uri.toString(),
                    mimeType = mimeType,
                    name = "e${estateId}p${System.currentTimeMillis()}"
                )
                estateRepository.addEstatePicture(newMedia)
                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error adding estate media: $e")
                false
            }
        }
    }

    override suspend fun removeEstateMedia(estateId: Long, mediaId: Long): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                estateRepository.deleteEstateMediaById(mediaId)
                true
            } catch (e: Exception) {
                Log.e("EditEstateUseCase", "Error removing estate media: $e")
                false
            }
        }
    }

    override suspend fun getEstateDetails(estateId: Long): Flow<EstateWithDetails> {
        return estateRepository.getEstateWithDetailById(estateId)
    }

    override suspend fun getInterestPoints(estateId: Long): Flow<List<EstateInterestPoints>> {
        return estateRepository.getInterestPointsByEstateId(estateId)
    }

    override suspend fun getEstateMedia(estateId: Long): Flow<List<EstateMedia>> {
        return estateRepository.getEstatePictures(estateId)
    }

    private suspend fun getMimeType(uri: Uri): String = withContext(Dispatchers.IO) {
        val contentResolver = context.contentResolver
        contentResolver.getType(uri) ?: run {
            val fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension.lowercase(Locale.ROOT)) ?: "application/octet-stream"
        }
    }
}