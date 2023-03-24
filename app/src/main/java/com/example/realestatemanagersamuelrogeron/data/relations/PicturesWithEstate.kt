package com.example.realestatemanagersamuelrogeron.data.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.model.EstatePictures

data class PicturesWithEstate(
    @Embedded val estate: Estate,
    @Relation(
        parentColumn = "id",
        entityColumn = "estateId"
    )
    val estatePictures: List<EstatePictures>
)
