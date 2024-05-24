package com.example.realestatemanagersamuelrogeron.domain.model

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
)