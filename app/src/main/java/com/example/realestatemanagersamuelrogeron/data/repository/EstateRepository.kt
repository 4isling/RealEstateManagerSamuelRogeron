package com.example.realestatemanagersamuelrogeron.data.repository

import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithPictures
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class EstateRepository @Inject constructor(
    private val estateDao: EstateDao
){
    fun addEstate(estate: Estate): Long{
        return estateDao.createEstate(estate = estate)
    }
    fun addEstatePictures(estatePictures: List<EstateMedia>){
        for (item in estatePictures) {
            estateDao.insertEstatePicture(item)
        }
    }
    fun addEstateInterestPoints(estateInterestPoints: List<EstateInterestPoints>) {
        for (item in estateInterestPoints) {
            estateDao.insertEstateInterestPoints(item)
        }
    }
    fun addEstateInterestPoint(estateInterestPoints: EstateInterestPoints){
        estateDao.insertEstateInterestPoints(estateInterestPoints)
    }
    fun updateEstate(estate: Estate, interestPoints: List<EstateInterestPoints>, pictures: List<EstateMedia>){
        estateDao.updateEstate(estate)
        for (item in interestPoints){
            estateDao.updateInterestPoint(item)
        }
        for (item in pictures){
            estateDao.updatePicture(item)
        }
    }
    fun getAllEstates(): Flow<List<Estate>> =
    estateDao.getAllEstates()
    fun getAllEstatesOrderedByGrowPrice():Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByGrowPrice()
    fun getAllEstatesOrderedByDecendPrice():Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByDecendPrice()
    fun getAllEstatesOrderedByGrowRent():Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByGrowRent()
    fun getAllEstatesOrderedByDecendRent():Flow<List<Estate>> =
        estateDao.getAllEstatesOrderedByDecendRent()

    fun delete(estate: Estate) {
        estateDao.delete(estate)
        estateDao.deleteAllPicturesWithEstate(estate.id)
        estateDao.deleteAllInterestPointWithEstate(estate.id)
    }
    fun deleteEstateById(estateId: Long){
        estateDao.deleteEstateById(estateId = estateId)
        estateDao.deleteAllPicturesWithEstate(estateId)
        estateDao.deleteAllInterestPointWithEstate(estateId = estateId)
    }

    fun getEstateById(id: Long): Flow<Estate> {
        return estateDao.getEstateById(estateId = id)
    }

    fun addEstatePicture(estateMedia: EstateMedia) {
        estateDao.insertEstatePicture(estateMedia = estateMedia)
    }
    fun getEstatePictures(estateId: Long): Flow<List<EstateMedia>>{
        return estateDao.getEstatePictures(estateId)
    }
    fun getEstateInterestPoints(estateId: Long):Flow<List<EstateInterestPoints>>{
        return estateDao.getEstateInterestPoints(estateId = estateId)
    }

    fun updateEstateLatLng(estateId: Long, lat: Double, lng: Double){
        estateDao.updateEstateLatLng(estateId,lat,lng)
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
}