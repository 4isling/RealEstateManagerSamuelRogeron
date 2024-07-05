package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

enum class SortOrder {
    ASCENDING,
    DESCENDING
}

data class EstateFilter(
    val typeOfEstate: String? = null,
    val typeOfOffer: String? = null,
    val minPrice: Int = 0,
    val maxPrice: Int = Int.MAX_VALUE,
    val etage: String? = null,
    val city: String? = null,
    val region: String? = null,
    val country: String? = null,
    val minSurface: Int = 0,
    val maxSurface: Int = Int.MAX_VALUE,
    val interestPoints: List<String> = emptyList(),
    val minMediaCount: Int = 0,
    val requireLatLng: Int = 0
)

interface GetAllEstatesWithPicturesUseCase {
    suspend fun execute(filter: EstateFilter): Flow<List<EstateWithDetails>>
}

class GetAllEstatesWithPicturesUseCaseImpl @Inject constructor(private val estateRepository: EstateRepository) :
    GetAllEstatesWithPicturesUseCase {
    override suspend fun execute(filter: EstateFilter): Flow<List<EstateWithDetails>> {
        val TAG = "GetEstateWithPictures"
        return try {
                estateRepository.getFilteredEstates(
                    filter
                )

        } catch(e: Exception) {
            flow { emit(emptyList<EstateWithDetails>()) }
        }
    }
}