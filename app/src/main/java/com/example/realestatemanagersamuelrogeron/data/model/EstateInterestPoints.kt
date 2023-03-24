package com.example.realestatemanagersamuelrogeron.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estate_interest_points")
data class EstateInterestPoints(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val estateId: Int = 0,
    val interestPointsName: String =""
)
