package com.example.realestatemanagersamuelrogeron.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "estate_pictures",
    /*foreignKeys = [
        ForeignKey(
            entity = Estate::class,
            parentColumns = ["id"],
            childColumns = ["estateId"],
            onDelete = CASCADE
            )
    ]*/
)
data class EstateMedia(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val estateId: Long = 0,
    val uri: String,
    val name: String,
)
