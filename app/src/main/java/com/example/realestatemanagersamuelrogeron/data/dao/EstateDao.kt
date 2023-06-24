package com.example.realestatemanagersamuelrogeron.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.data.relations.InterestPointsWithEstate
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {
    @Insert(onConflict = REPLACE)
    fun createEstate(estate: Estate): Long

    @Insert(onConflict = REPLACE)
    fun insertEstatePicture(estatePictures: EstatePictures)

    @Insert(onConflict = REPLACE)
    fun insertEstateInterestPoints(estateInterestPoints: EstateInterestPoints)

    @Query("SELECT * FROM estates")
    fun getAllEstates(): Flow<List<Estate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE id = :id")
    fun getPicturesWithEstate(id: Long): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE id = :id")
    fun getInterestPointsWithEstate(id: Long): Flow<List<InterestPointsWithEstate>>

    @Delete
    fun delete(estate: Estate)

    @Delete
    fun delete(interestPoint: EstateInterestPoints)

    @Delete
    fun delete(pictures: EstatePictures)

    @Query("DELETE FROM estate_interest_points WHERE estateId = :estateId")
    fun deleteAllInterestPointWithEstate(estateId: Long)

    @Query("DELETE FROM estate_pictures WHERE estateId = :estateId")
    fun deleteAllPicturesWithEstate(estateId: Long)

    @Query("DELETE FROM estates WHERE id = :estateId")
    fun deleteEstateById(estateId: Long)
    //Filtered
    @Query("SELECT * FROM estates ORDER BY sellingPrice ASC")
    fun getAllEstatesOrderedByGrowPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY sellingPrice DESC")
    fun getAllEstatesOrderedByDecendPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY rent ASC")
    fun getAllEstatesOrderedByGrowRent(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY rent DESC")
    fun getAllEstatesOrderedByDecendRent(): Flow<List<Estate>>
    @Query("SELECT * FROM estates WHERE id = :estateId")
    fun getEstateById(estateId: Long): Flow<Estate>
    @Query("SELECT * FROM estate_pictures WHERE estateId = :estateId")
    fun getEstatePictures(estateId: Long): Flow<List<EstatePictures>>

    @Query("SELECT * FROM estate_interest_points WHERE estateId = :estateId")
    fun getEstateInterestPoints(estateId: Long): Flow<List<EstateInterestPoints>>
}