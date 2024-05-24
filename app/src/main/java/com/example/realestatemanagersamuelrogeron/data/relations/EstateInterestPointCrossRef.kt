package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Entity

@Entity(
    tableName = "estate_interest_point_cross_ref",
    primaryKeys = ["estateId", "estateInterestPointId"]
)
data class EstateInterestPointCrossRef(
    val estateId: Long,
    val estateInterestPointId: Long
)