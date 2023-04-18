package com.example.realestatemanagersamuelrogeron.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.FileDescriptor

@Entity(tableName = "estates")
data class Estate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",//
    val typeOfEstate: String = "",//
    var typeOfOffer: String = "",//
    val sellingPrice: Int?,//
    val rent: Int?,//
    val surface: Int = 0,//
    val nbRooms: Int = 0,
    val etage: String = "",//
    val address: String = "",//
    val zipCode: String,
    val city: String,
    val description: String = "",//
    val status: Boolean = true,
    val addDate: String,
    val sellDate: String,
    val agent: String = "Stephane",
    val isFav: Boolean = false
)

