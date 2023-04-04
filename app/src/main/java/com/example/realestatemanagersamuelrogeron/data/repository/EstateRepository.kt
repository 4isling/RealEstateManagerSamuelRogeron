package com.example.realestatemanagersamuelrogeron.data.repository

import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.data.model.EstatePictures
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface EstateRepository {
    suspend fun addEstate(estate: Estate)
    suspend fun addEstatePictures(estatePictures: List<EstatePictures>)
    suspend fun addEstateInterestPoints(estateInterestPoints: List<EstateInterestPoints>)
    suspend fun getAllEstate(): Flow<List<Estate>>

}
@ActivityScoped
class EstateRepositoryImpl @Inject constructor(private val estateDao: EstateDao): EstateRepository {
    override suspend fun addEstate(estate: Estate){
        estateDao.createEstate(estate = estate)
    }

    override suspend fun addEstateInterestPoints(estateInterestPoints: List<EstateInterestPoints>) {
        for(item in estateInterestPoints ) {
            estateDao.insertEstateInterestPoints(item)
        }
    }

    override suspend fun addEstatePictures(estatePictures: List<EstatePictures>) {
        for(item in estatePictures){
            estateDao.insertEstatePicture(item)
        }
    }


    override suspend fun getAllEstate(): Flow<List<Estate>> =
        estateDao.getAllEstates()
}