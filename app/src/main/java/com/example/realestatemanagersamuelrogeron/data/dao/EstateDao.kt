package com.example.realestatemanagersamuelrogeron.data.dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.data.relations.InterestPointsWithEstate
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia
import com.example.realestatemanagersamuelrogeron.domain.usecases.EstateFilter
import kotlinx.coroutines.flow.Flow

@Dao
interface EstateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createEstate(estate: Estate): Long

    @Update
    fun updateEstate(estate: Estate): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstatePicture(estateMedia: EstateMedia): Long

    @Update
    fun updatePicture(estateMedia: EstateMedia): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstateInterestPoints(estateInterestPoints: EstateInterestPoints): Long

    @Update
    fun updateInterestPoint(estateInterestPoints: EstateInterestPoints): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossRef(crossRef: EstateInterestPointCrossRef): Long

    @Update
    fun updateCrossRef(crossRef: EstateInterestPointCrossRef): Int

    @Delete
    fun deleteCrossRef(crossRef: EstateInterestPointCrossRef): Int

    @Query("SELECT * FROM estates")
    fun getAllEstates(): Flow<List<Estate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE estateId = :id")
    fun getPicturesWithEstate(id: Long): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates WHERE estateId = :id")
    fun getInterestPointsWithEstate(id: Long): Flow<List<InterestPointsWithEstate>>

    @Delete
    fun delete(estate: Estate): Int

    @Delete
    fun delete(interestPoint: EstateInterestPoints): Int

    @Delete
    fun delete(pictures: EstateMedia): Int

    @Query("DELETE FROM estate_pictures WHERE id = :mediaId")
    fun deleteEstateMediaById(mediaId: Long): Int

    @Query("DELETE FROM estate_interest_points WHERE estateInterestPointId = :interestPointId")
    fun deleteInterestPointById(interestPointId: Long): Int

    @Query("DELETE FROM estate_pictures WHERE estateId = :estateId")
    fun deleteAllPicturesWithEstate(estateId: Long): Int

    @Query("DELETE FROM estates WHERE estateId = :estateId")
    fun deleteEstateById(estateId: Long): Int

    @Query("DELETE FROM estate_interest_point_cross_ref WHERE estateId = :estateId")
    fun deleteAllCrossRefsWithEstate(estateId: Long): Int

    @Query("SELECT * FROM estates ORDER BY price ASC")
    fun getAllEstatesOrderedByGrowPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY price DESC")
    fun getAllEstatesOrderedByDecendPrice(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY price ASC")
    fun getAllEstatesOrderedByGrowRent(): Flow<List<Estate>>

    @Query("SELECT * FROM estates ORDER BY price DESC")
    fun getAllEstatesOrderedByDecendRent(): Flow<List<Estate>>

    @Query("SELECT * FROM estates WHERE estateId = :estateId")
    fun getEstateById(estateId: Long): Flow<Estate>

    @Query("SELECT * FROM estate_pictures WHERE estateId = :estateId")
    fun getEstatePictures(estateId: Long): Flow<List<EstateMedia>>

    @Query("SELECT * FROM estate_interest_points")
    fun getAllInterestPoints(): Flow<List<EstateInterestPoints>>

    @Query("SELECT * FROM estate_interest_points WHERE interestPointsName = :name")
    fun getInterestPointByName(name: List<String>): Flow<EstateInterestPoints>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllInterestPoints(interestPoints: List<EstateInterestPoints>): List<Long>

    @Query("UPDATE estates SET lat = :lat, lng = :lng WHERE estateId = :estateId")
    fun updateEstateLatLng(estateId: Long, lat: Double, lng: Double): Int

    @Transaction
    @Query("SELECT * FROM estates")
    fun getAllEstatesWithPictures(): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates ORDER BY price ASC")
    fun getEstateWithPictureOrderedByGrowPrice(): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates ORDER BY price DESC")
    fun getAllEstatesWithPictureOrderedByDecendPrice(): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates ORDER BY price ASC")
    fun getAllEstatesWithPictureOrderedByGrowRent(): Flow<List<PicturesWithEstate>>

    @Transaction
    @Query("SELECT * FROM estates ORDER BY price DESC")
    fun getAllEstatesWithPictureOrderedByDecendRent(): Flow<List<PicturesWithEstate>>

    @Query("SELECT * FROM estates WHERE lat IS NULL OR lng IS NULL")
    fun getEstateWithoutLatLng(): Flow<List<Estate>>

    @Query(
        """
    SELECT estates.*
    FROM estates
    LEFT JOIN estate_pictures ON estates.estateId = estate_pictures.estateId
    WHERE (:typeOfEstate IS NULL OR typeOfEstate = :typeOfEstate)
      AND (status IS 1)
      AND (:typeOfOffer IS NULL OR typeOfOffer = :typeOfOffer)
      AND (:minPrice IS NULL OR price >= :minPrice)
      AND (:maxPrice IS NULL OR price <= :maxPrice)
      AND (:etage IS NULL OR etage = :etage)
      AND (:city IS NULL OR city = :city)
      AND (:region IS NULL OR region = :region)
      AND (:country IS NULL OR country = :country)
      AND (:minSurface IS NULL OR surface >= :minSurface)
      AND (:maxSurface IS NULL OR surface <= :maxSurface)
      AND estates.estateId IN (
          SELECT estateId
          FROM estate_interest_point_cross_ref
          WHERE estateInterestPointId IN (
              SELECT estateInterestPointId
              FROM estate_interest_points
              WHERE (:interestPointsSize = 0 OR interestPointsName IN (:interestPoints))
          )
          GROUP BY estateId
          HAVING COUNT(DISTINCT estateInterestPointId) >= :interestPointsSize
      )
      AND (:requireLatLng = 0 OR (lat IS NOT NULL AND lat != '' AND lng IS NOT NULL AND lng != ''))
    GROUP BY estates.estateId
    HAVING COUNT(DISTINCT estate_pictures.id) >= :minMediaCount
    ORDER BY price ASC
"""
    )
    fun getFilteredEstates(
        typeOfEstate: String? = null,
        typeOfOffer: String? = null,
        minPrice: Int = 0,
        maxPrice: Int = Int.MAX_VALUE,
        etage: String? = null,
        city: String? = null,
        region: String? = null,
        country: String? = null,
        minSurface: Int = 0,
        maxSurface: Int = Int.MAX_VALUE,
        interestPoints: List<String> = emptyList(),
        interestPointsSize: Int = 0,
        minMediaCount: Int = 0,
        requireLatLng: Int = 0
    ): Flow<List<EstateWithDetails>>


    fun getFilteredEstates(filter: EstateFilter): Flow<List<EstateWithDetails>> {
        return getFilteredEstates(
            typeOfEstate = filter.typeOfEstate,
            typeOfOffer = filter.typeOfOffer,
            minPrice = filter.minPrice,
            maxPrice = filter.maxPrice,
            etage = filter.etage,
            city = filter.city,
            region = filter.region,
            country = filter.country,
            minSurface = filter.minSurface,
            maxSurface = filter.maxSurface,
            interestPoints = filter.interestPoints,
            interestPointsSize = filter.interestPoints.size,
            minMediaCount = filter.minMediaCount,
            requireLatLng = filter.requireLatLng
        )
    }

    @Query("""
        SELECT * FROM estates
        WHERE estateId = :estateId
    """)
    fun getEstateWithDetailById(estateId: Long): Flow<EstateWithDetails>

    @Query("""
        SELECT e.*, 
        (6371 * acos(cos(radians(:userLat)) * cos(radians(e.lat)) * cos(radians(e.lng) - radians(:userLng)) + sin(radians(:userLat)) * sin(radians(e.lat)))) AS distance
        FROM estates e
        WHERE e.status = :status
        ORDER BY distance ASC
    """)
    fun getEstatesByProximity(
        status: Boolean,
        userLat: Double,
        userLng: Double
    ): Flow<List<PicturesWithEstate>>

    @Query("SELECT * FROM estates")
    fun getAllEstatesCursor(): Cursor

    @Query("SELECT * FROM estates WHERE estateId = :estateId")
    fun getEstateByIdCursor(estateId: Long): Cursor

    @Query("SELECT * FROM estate_interest_points")
    fun getAllInterestPointsCursor(): Cursor

    @Query("SELECT * FROM estate_interest_points WHERE estateInterestPointId = :id")
    fun getInterestPointByIdCursor(id: Long): Cursor

    @Query("SELECT * FROM estate_pictures")
    fun getAllEstateMediaCursor(): Cursor

    @Query("SELECT * FROM estate_pictures WHERE id = :id")
    fun getEstateMediaByIdCursor(id: Long): Cursor

    @RawQuery
    fun rawQuery(query: SupportSQLiteQuery): Cursor
}
