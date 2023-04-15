package com.example.realestatemanagersamuelrogeron.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.util.foreignKeyCheck

@Entity(
    tableName = "estate_pictures",
    foreignKeys = [
        ForeignKey(
            entity = Estate::class,
            parentColumns = ["id"],
            childColumns = ["estateId"],
            onDelete = CASCADE
            )
    ]
)
data class EstatePictures(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val estateId: Long = 0,
    val pictureUri: String,
    val name: String,
)
