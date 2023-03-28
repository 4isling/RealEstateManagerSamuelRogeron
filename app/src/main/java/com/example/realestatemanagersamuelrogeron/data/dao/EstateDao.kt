package com.example.realestatemanagersamuelrogeron.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.data.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.data.relations.InterestPointsWithEstate
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {
    @Insert(onConflict = REPLACE)
    suspend fun createEstate(estate: Estate): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertEstatePicture(estatePictures: EstatePictures)

    @Insert(onConflict = REPLACE)
    suspend fun insertEstateInterestPoints(estateInterestPoints: EstateInterestPoints)

    @Query("SELECT * FROM estates")
    fun getAllEstates(): Flow<List<Estate>>

    @Transaction
    @Query("SELECT * FROM estate_pictures WHERE estateId = :id")
    suspend fun getPicturesWithEstate(estateId: Int): List<PicturesWithEstate>

    @Transaction
    @Query("SELECT * FROM estate_interest_points WHERE estateId = :id")
    suspend fun getInterestPointsWithEstate(estateId: Int): List<InterestPointsWithEstate>

    @Delete
    fun delete(estate: Estate)

    @Delete
    fun delete(interestPoint: EstateInterestPoints)

    @Delete
    fun delete(pictures: EstatePictures)

    @Query("DELETE * FROM estate_interest_points WHERE estateId = :estateId")
    fun deleteAllInterestPointWithEstate(estateId: Int)

    @Query("DELETE * FROM estate_pictures WHERE estateId = :estateId")
    fun deleteAllPicturesWithEstate(estateId: Int)


}