package com.example.realestatemanagersamuelrogeron.data.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estate_pictures")
data class EstatePictures(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val estateId: Long = 0,
    val pictureUri: Uri,
    val name: String,
    val width: Int,
    val height: Int,
    val isExternal: Boolean = false
)
