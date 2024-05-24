package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.realestatemanagersamuelrogeron.domain.model.Estate


data class EstatesWithInterestPoints(
    @Embedded val estatesWithInterestPoints: EstatesWithInterestPoints,
    @Relation(
        parentColumn = "estateInterestPointId",
        entityColumn = "id",
        associateBy = Junction(EstateInterestPointCrossRef::class)
    )
    val estates: List<Estate>
)
