package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints

data class InterestPointsWithEstate(
    @Embedded val estate: Estate,
    @Relation(
        parentColumn = "id",
        entityColumn = "estateId"
    )
    val estateInterestPoints: List<EstateInterestPoints>
)
