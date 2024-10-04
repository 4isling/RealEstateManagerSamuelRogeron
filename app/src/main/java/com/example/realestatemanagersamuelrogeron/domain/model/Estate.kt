package com.example.realestatemanagersamuelrogeron.domain.model

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "estates")
data class Estate(
    @PrimaryKey(autoGenerate = true)
    val estateId: Long = 0,
    val title: String = "",     //
    val typeOfEstate: String = "",  // can be "Apartment", "House", "Garage", "Land-field", "Warehouse"
    var typeOfOffer: String = "",   // can be Rent or Sell
    val etage: String = "",     //
    val address: String = "",   //
    val zipCode: String = "",
    val city: String = "",
    val region: String = "",
    val country: String = "",
    val description: String = "",//
    val addDate: Long,
    val sellDate: Long? = null,
    val agent: String = "Stephane",
    val price: Int = 0,     //
    val surface: Int,       //
    val nbRooms: Int,
    val status: Boolean = true,
    val isFav: Boolean = false,
    val lat: Double? = null,
    val lng: Double? = null,
    val staticMapUrl: String? = null
){
    companion object {
        fun fromContentValues(values: ContentValues): Estate {
            return Estate(
                estateId = values.getAsLong("estateId") ?: 0L,
                title = values.getAsString("title") ?: "",
                typeOfEstate = values.getAsString("typeOfEstate") ?: "",
                typeOfOffer = values.getAsString("typeOfOffer") ?: "",
                etage = values.getAsString("etage") ?: "",
                address = values.getAsString("address") ?: "",
                zipCode = values.getAsString("zipCode") ?: "",
                city = values.getAsString("city") ?: "",
                region = values.getAsString("region") ?: "",
                country = values.getAsString("country") ?: "",
                description = values.getAsString("description") ?: "",
                addDate = values.getAsLong("addDate") ?: 0L,
                sellDate = values.getAsLong("sellDate"),
                agent = values.getAsString("agent") ?: "Stephane",
                price = values.getAsInteger("price") ?: 0,
                surface = values.getAsInteger("surface") ?: 0,
                nbRooms = values.getAsInteger("nbRooms") ?: 0,
                status = values.getAsBoolean("status") ?: true,
                isFav = values.getAsBoolean("isFav") ?: false,
                lat = values.getAsDouble("lat"),
                lng = values.getAsDouble("lng"),
                staticMapUrl = values.getAsString("staticMapUrl")
            )
        }
    }
}
/**
 * TODO get lat-lng with geocoder form the address in a fun
 */