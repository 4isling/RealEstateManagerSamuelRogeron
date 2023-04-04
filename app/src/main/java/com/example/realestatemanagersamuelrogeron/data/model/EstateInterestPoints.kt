package com.example.realestatemanagersamuelrogeron.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "estate_interest_points",
   /* foreignKeys = [
        ForeignKey(
            entity = Estate::class,
            parentColumns = ["id"],
            childColumns = ["estateId"],
            onDelete = ForeignKey.CASCADE
        )
    ]*/
)
data class EstateInterestPoints(
    @PrimaryKey(autoGenerate = true)
    val estateInterestPointId: Long = 0,
    val estateId: Long = 0,
    val interestPointsName: String =""
)
