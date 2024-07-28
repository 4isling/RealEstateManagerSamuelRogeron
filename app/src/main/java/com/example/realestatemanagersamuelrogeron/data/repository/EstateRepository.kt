package com.example.realestatemanagersamuelrogeron.data.repository

import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(
    private val estateDao: EstateDao
) {
    fun addEstate(estate: Estate): Long {
        return estateDao.createEstate(estate = estate)
    }

    fun addEstatePictures(estatePictures: List<EstateMedia>) {
        for (item in estatePictures) {
            estateDao.insertEstatePicture(item)
        }
    }

    fun addEstateInterestPoints(estateInterestPoints: List<EstateInterestPoints>) {
        for (item in estateInterestPoints) {
            estateDao.insertEstateInterestPoints(item)
        }
    }

    fun addEstateInterestPoint(estateInterestPoints: EstateInterestPoints): Long {
        return estateDao.insertEstateInterestPoints(estateInterestPoints)
    }

    fun addEstateInterestPointCrossRef(crossRef: EstateInterestPointCrossRef) {
        estateDao.insertCrossRef(crossRef)
    }

    fun updateEstate(
        estate: Estate,
    ) {
        estateDao.updateEstate(estate)
    }

    fun getAllEstates(): Flow<List<Estate>> = estateDao.getAllEstates()

    fun getAllEstatesOrderedByGrowPrice(): Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByGrowPrice()

    fun getAllEstatesOrderedByDecendPrice(): Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByDecendPrice()

    fun getAllEstatesOrderedByGrowRent(): Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByGrowRent()

    fun getAllEstatesOrderedByDecendRent(): Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByDecendRent()

    fun delete(estate: Estate) {
        estateDao.delete(estate)
        estateDao.deleteAllPicturesWithEstate(estate.estateId)
        estateDao.deleteAllCrossRefsWithEstate(estate.estateId)
    }

    fun deleteEstateById(estateId: Long) {
        estateDao.deleteEstateById(estateId = estateId)
        estateDao.deleteAllPicturesWithEstate(estateId)
        estateDao.deleteAllCrossRefsWithEstate(estateId)
    }

    fun getEstateById(id: Long): Flow<Estate> {
        return estateDao.getEstateById(estateId = id)
    }


    fun addEstatePicture(estateMedia: EstateMedia) {
        estateDao.insertEstatePicture(estateMedia = estateMedia)
    }

    fun getEstatePictures(estateId: Long): Flow<List<EstateMedia>> {
        return estateDao.getEstatePictures(estateId)
    }

    fun updateEstateLatLng(estateId: Long, lat: Double, lng: Double) {
        estateDao.updateEstateLatLng(estateId, lat, lng)
    }

    fun getAllEstatesWithPictures(): Flow<List<EstateWithPictures>> {
        return estateDao.getAllEstatesWithPictures()
            .map { list ->
                list.map { picturesWithEstate ->
                    EstateWithPictures(
                        estate = picturesWithEstate.estate,
                        pictures = picturesWithEstate.estatePictures
                    )
                }
            }
    }

    fun getEstateWithPictureOrderedByGrowPrice(): Flow<List<EstateWithPictures>> {
        return estateDao.getEstateWithPictureOrderedByGrowPrice()
            .map { list ->
                list.map { picturesWithEstate ->
                    EstateWithPictures(
                        estate = picturesWithEstate.estate,
                        pictures = picturesWithEstate.estatePictures
                    )
                }
            }
    }

    fun getAllEstatesWithPictureOrderedByDecendPrice(): Flow<List<EstateWithPictures>> {
        return estateDao.getAllEstatesWithPictureOrderedByDecendPrice()
            .map { list ->
                list.map { picturesWithEstate ->
                    EstateWithPictures(
                        estate = picturesWithEstate.estate,
                        pictures = picturesWithEstate.estatePictures
                    )
                }
            }
    }

    fun getAllEstatesWithPictureOrderedByGrowRent(): Flow<List<EstateWithPictures>> {
        return estateDao.getAllEstatesWithPictureOrderedByGrowRent()
            .map { list ->
                list.map { picturesWithEstate ->
                    EstateWithPictures(
                        estate = picturesWithEstate.estate,
                        pictures = picturesWithEstate.estatePictures
                    )
                }
            }
    }

    fun getAllEstatesWithPictureOrderedByDecendRent(): Flow<List<EstateWithPictures>> {
        return estateDao.getAllEstatesWithPictureOrderedByDecendRent()
            .map { list ->
                list.map { picturesWithEstate ->
                    EstateWithPictures(
                        estate = picturesWithEstate.estate,
                        pictures = picturesWithEstate.estatePictures
                    )
                }
            }
    }

    fun getEstateWithoutLatLng(): Flow<List<Estate>> {
        return estateDao.getEstateWithoutLatLng()
    }

    fun getAllInterestPoints(): Flow<List<EstateInterestPoints>> {
        return estateDao.getAllInterestPoints()
    }


    fun getInterestPointsByEstateId(estateId: Long): Flow<List<EstateInterestPoints>> {
        val interestPointsWithEstateFlow = estateDao.getInterestPointsWithEstate(estateId)
        return interestPointsWithEstateFlow.map { list ->
            list.flatMap { it.estateInterestPoints }
        }
    }

    // New functions added
    fun updatePicture(estateMedia: EstateMedia) {
        estateDao.updatePicture(estateMedia)
    }

    fun updateInterestPoint(estateInterestPoints: EstateInterestPoints) {
        estateDao.updateInterestPoint(estateInterestPoints)
    }

    fun updateCrossRef(crossRef: EstateInterestPointCrossRef) {
        estateDao.updateCrossRef(crossRef)
    }

    fun delete(interestPoint: EstateInterestPoints):Int {
        return estateDao.delete(interestPoint)
    }

    fun delete(pictures: EstateMedia) {
        estateDao.delete(pictures)
    }

    fun deleteInterestPointById(interestPointId: Long): Int {
        return estateDao.deleteInterestPointById(interestPointId)
    }


    fun getFilteredEstates(
        typeOfEstate: String?,
        typeOfOffer: String?,
        minPrice: Int,
        maxPrice: Int,
        etage: String?,
        city: String?,
        region: String?,
        country: String?,
        minSurface: Int,
        maxSurface: Int,
        interestPoints: List<String>,
        interestPointsSize: Int,
        minMediaCount: Int
    ): Flow<List<EstateWithDetails>> {
        return estateDao.getFilteredEstates(
            typeOfEstate,
            typeOfOffer,
            minPrice,
            maxPrice,
            etage,
            city,
            region,
            country,
            minSurface,
            maxSurface,
            interestPoints,
            interestPointsSize,
            minMediaCount
        )
    }

    fun getFilteredEstates(filter: EstateFilter): Flow<List<EstateWithDetails>>{
        return estateDao.getFilteredEstates(filter)
    }

    fun getEstateWithDetailById(estateId: Long): Flow<EstateWithDetails> {
        return estateDao.getEstateWithDetailById(estateId)
    }

    fun getEstatesByProximity(
        status: Boolean,
        userLat: Double,
        userLng: Double
    ): Flow<List<EstateWithPictures>> {
        return estateDao.getEstatesByProximity(
            status = status,
            userLat = userLat,
            userLng = userLng
        ).map { list ->
            list.map { picturesWithEstate ->
                EstateWithPictures(
                    estate = picturesWithEstate.estate,
                    pictures = picturesWithEstate.estatePictures
                )
            }
        }
    }

}