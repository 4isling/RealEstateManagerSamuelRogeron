package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints

data class InterestPointsWithEstate(
    @Embedded val estate: Estate,
    @Relation(
        parentColumn = "estateId",
        entityColumn = "estateInterestPointId",
        associateBy = Junction(EstateInterestPointCrossRef::class)
    )
    val estateInterestPoints: List<EstateInterestPoints>
)