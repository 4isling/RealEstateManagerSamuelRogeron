package com.example.realestatemanagersamuelrogeron.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import com.example.realestatemanagersamuelrogeron.data.model.Estate

@Dao
interface EstateDao {
    @Insert(onConflict = REPLACE)
    suspend fun createEstate(estate: Estate): Long

}