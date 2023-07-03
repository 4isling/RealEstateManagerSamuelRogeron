package com.example.realestatemanagersamuelrogeron.data.repository

import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EstateRepository @Inject constructor(
    private val estateDao: EstateDao
){
    fun addEstate(estate: Estate): Long{
        return estateDao.createEstate(estate = estate)
    }
    fun addEstatePictures(estatePictures: List<EstatePictures>){
        for (item in estatePictures) {
            estateDao.insertEstatePicture(item)
        }
    }
    fun addEstateInterestPoints(estateInterestPoints: List<EstateInterestPoints>) {
        for (item in estateInterestPoints) {
            estateDao.insertEstateInterestPoints(item)
        }
    }
    fun updateEstate(estate: Estate, interestPoints: List<EstateInterestPoints>, pictures: List<EstatePictures>){
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
        return estateDao.getEstateById(id)
    }

    fun addEstatePicture(estatePictures: EstatePictures) {
        estateDao.insertEstatePicture(estatePictures = estatePictures)
    }
    fun getEstatePictures(estateId: Long): Flow<List<EstatePictures>>{
        return estateDao.getEstatePictures(estateId)
    }
    fun getEstateInterestPoints(estateId: Long):Flow<List<EstateInterestPoints>>{
        return estateDao.getEstateInterestPoints(estateId = estateId)
    }
}
