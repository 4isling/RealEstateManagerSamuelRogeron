package com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel

import android.net.Uri
import com.example.realestatemanagersamuelrogeron.data.relations.EstateWithDetails
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints


data class AddEstateState(
    val estateWithDetails: EstateWithDetails = EstateWithDetails(
        estate = Estate(
            estateId = 0L,
            title = "",
            typeOfEstate = "",
            typeOfOffer = "",
            etage = "",
            address = "",
            zipCode = "",
            city = "",
            region = "",
            country = "",
            description = "",
            addDate = System.currentTimeMillis(),
            sellDate = null,
            agent = "",
            price = 0,
            surface = 0,
            nbRooms = 0,
            status = true,
            isFav = false,
            lat = null,
            lng = null,
            staticMapUrl = null
        ),
        estatePictures = emptyList(),
        estateInterestPoints = emptyList()
    ),
    val allInterestPoints: List<EstateInterestPoints> = emptyList(),
    val newMediaUris: List<Uri> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEuro: Boolean = false,
    val displayPrice: String = "",
    val titleError: Boolean = false,
    val typeError: Boolean = false,
    val offerError: Boolean = false,
    val priceError: Boolean = false,
    val surfaceError: Boolean = false,
    val nbRoomsError: Boolean = false,
    val etagesError: Boolean = false,
    val addressError: Boolean = false,
    val zipCodeError: Boolean = false,
    val cityError: Boolean = false,
    val regionError: Boolean = false,
    val countryError: Boolean = false,
    val descriptionError: Boolean = false,
    val mediaError: Boolean = false,
    val interestPointError: Boolean = false,
    val isSaveEnabled: Boolean = false,
    val isEstateSaved: Boolean = false
)