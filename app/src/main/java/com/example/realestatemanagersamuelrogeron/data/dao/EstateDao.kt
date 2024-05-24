package com.example.realestatemanagersamuelrogeron.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.relations.InterestPointsWithEstate
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate): Long

    @Update
    fun updateEstate(estate: Estate)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstatePicture(estateMedia: EstateMedia)

    @Update
    fun updatePicture(estateMedia: EstateMedia)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstateInterestPoints(estateInterestPoints: EstateInterestPoints): Long

    @Update
    fun updateInterestPoint(estateInterestPoints: EstateInterestPoints)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(crossRef: EstateInterestPointCrossRef)

    @Update
    fun updateCrossRef(crossRef: EstateInterestPointCrossRef)

    @Delete
    fun deleteCrossRef(crossRef: EstateInterestPointCrossRef)

    @Query("SELECT * FROM estates")
    fun getAllEstates(): Flow<List<Estate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE estateId = :id")
    fun getPicturesWithEstate(id: Long): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE estateId = :id")
    fun getInterestPointsWithEstate(id: Long): Flow<List<InterestPointsWithEstate>>

    @Delete
    fun delete(estate: Estate)

    @Delete
    fun delete(interestPoint: EstateInterestPoints)

    @Delete
    fun delete(pictures: EstateMedia)

    @Query("DELETE FROM estate_interest_points WHERE estateInterestPointId = :interestPointId")
    fun deleteInterestPointById(interestPointId: Long)

    @Query("DELETE FROM estate_pictures WHERE estateId = :estateId")
    fun deleteAllPicturesWithEstate(estateId: Long)

    @Query("DELETE FROM estates WHERE estateId = :estateId")
    fun deleteEstateById(estateId: Long)

    @Query("DELETE FROM estate_interest_point_cross_ref WHERE estateId = :estateId")
    fun deleteAllCrossRefsWithEstate(estateId: Long)

    @Query("SELECT * FROM estates ORDER BY sellingPrice ASC")
    fun getAllEstatesOrderedByGrowPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY sellingPrice DESC")
    fun getAllEstatesOrderedByDecendPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY rent ASC")
    fun getAllEstatesOrderedByGrowRent(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY rent DESC")
    fun getAllEstatesOrderedByDecendRent(): Flow<List<Estate>>

    @Query("SELECT * FROM estates WHERE estateId = :estateId")
    fun getEstateById(estateId: Long): Flow<Estate>

    @Query("SELECT * FROM estate_pictures WHERE estateId = :estateId")
    fun getEstatePictures(estateId: Long): Flow<List<EstateMedia>>

    @Query("SELECT * FROM estate_interest_points")
    fun getAllInterestPoints(): Flow<List<EstateInterestPoints>>

    @Query("SELECT * FROM estate_interest_points WHERE interestPointsName = :name")
    fun getInterestPointByName(name: List<String>): Flow<EstateInterestPoints>

    @Query("SELECT * FROM estate_interest_points WHERE interestPointsName IN (:interestPointsNames)")
    suspend fun getInterestPointsByName(interestPointsNames: List<String>): List<EstateInterestPoints>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterestPoints(interestPoints: List<EstateInterestPoints>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllInterestPoints(interestPoints: List<EstateInterestPoints>)

    @Query("UPDATE estates SET lat = :lat, lng = :lng WHERE estateId = :estateId")
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

    /** todo create the query to get the estate with filter directly from room db */
}