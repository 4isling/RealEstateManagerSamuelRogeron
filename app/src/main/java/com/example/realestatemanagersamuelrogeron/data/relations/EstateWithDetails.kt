package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

data class EstateWithDetails(
    @Embedded val estate: Estate,
    @Relation(
        parentColumn = "estateId",
        entityColumn = "estateId"
    )
    val estatePictures: List<EstateMedia>,

    @Relation(
        parentColumn = "estateId",
        entity = EstateInterestPoints::class,
        entityColumn = "estateInterestPointId",
        associateBy = Junction(
            value = EstateInterestPointCrossRef::class,
            parentColumn = "estateId",
            entityColumn = "estateInterestPointId"
        )
    )
    val estateInterestPoints: List<EstateInterestPoints>
)
