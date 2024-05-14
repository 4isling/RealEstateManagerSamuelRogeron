package com.example.realestatemanagersamuelrogeron.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.data.relations.InterestPointsWithEstate
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {
    @Insert(onConflict = REPLACE)
    fun createEstate(estate: Estate): Long

    @Update
    fun updateEstate(estate: Estate)

    @Insert(onConflict = REPLACE)
    fun insertEstatePicture(estateMedia: EstateMedia)

    @Update
    fun updatePicture(estateMedia: EstateMedia)

    @Insert(onConflict = REPLACE)
    fun insertEstateInterestPoints(estateInterestPoints: EstateInterestPoints)

    @Update
    fun updateInterestPoint(estateInterestPoints: EstateInterestPoints)

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
    fun delete(pictures: EstateMedia)

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
    fun getEstatePictures(estateId: Long): Flow<List<EstateMedia>>

    @Query("SELECT * FROM estate_interest_points WHERE estateId = :estateId")
    fun getEstateInterestPoints(estateId: Long): Flow<List<EstateInterestPoints>>

    @Query("UPDATE estates SET lat = :lat, lng = :lng WHERE id = :estateId")
    fun updateEstateLatLng(estateId: Long, lat: Double, lng: Double)

    @Transaction
    @Query("SELECT * FROM estates")
    fun getAllEstatesWithPictures(): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates ORDER BY sellingPrice ASC")
    fun getEstateWithPictureOrderedByGrowPrice(): Flow<List<PicturesWithEstate>>
    @Transaction
    @Query("SELECT * FROM estates ORDER BY sellingPrice DESC")
    fun getAllEstatesWithPictureOrderedByDecendPrice(): Flow<List<PicturesWithEstate>>
    @Transaction
    @Query("SELECT * FROM estates ORDER BY rent ASC")
    fun getAllEstatesWithPictureOrderedByGrowRent(): Flow<List<PicturesWithEstate>>
    @Transaction
    @Query("SELECT * FROM estates ORDER BY rent DESC")
    fun getAllEstatesWithPictureOrderedByDecendRent(): Flow<List<PicturesWithEstate>>


    @Query("SELECT * FROM estates WHERE lat IS NULL OR lng IS NULL")
    fun getEstateWithoutLatLng(): Flow<List<Estate>>
}