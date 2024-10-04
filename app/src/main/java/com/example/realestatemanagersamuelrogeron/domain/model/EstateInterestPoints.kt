package com.example.realestatemanagersamuelrogeron.domain.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "estate_interest_points",
    indices = [Index(value = ["estateInterestPointId"])]
)
data class EstateInterestPoints(
    @PrimaryKey(autoGenerate = true)
    val estateInterestPointId: Long = 0,
    val interestPointsName: String ="",
    val iconCode: Int = 0,
){
    companion object {
        fun fromContentValues(values: ContentValues): EstateInterestPoints {
            return EstateInterestPoints(
                estateInterestPointId = values.getAsLong("estateInterestPointId") ?: 0L,
                interestPointsName = values.getAsString("interestPointsName") ?: "",
                iconCode = values.getAsInteger("iconCode") ?: 0
            )
        }
    }
}