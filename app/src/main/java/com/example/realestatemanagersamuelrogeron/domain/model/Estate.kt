package com.example.realestatemanagersamuelrogeron.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estates")
data class Estate(
    @PrimaryKey(autoGenerate = true)
    val estateId: Long = 0,
    val title: String = "",//
    val typeOfEstate: String = "",//
    var typeOfOffer: String = "",//
    val etage: String = "",//
    val address: String = "",//
    val zipCode: String = "",
    val city: String = "",
    val region: String = "",
    val country: String = "",
    val description: String = "",//
    val addDate: Long,
    val sellDate: Long? = null,
    val agent: String = "Stephane",
    val price: Int = 0,//
    val surface: Int,//
    val nbRooms: Int,
    val status: Boolean = true,
    val isFav: Boolean = false,
    val lat: Double? =null,
    val lng: Double? = null
)
/**
 * TODO get latlng with geocoder form the address in a fun
 */