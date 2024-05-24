package com.example.realestatemanagersamuelrogeron.data.state

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

public final data class DetailState(
    val estate: Estate = Estate(
        estateId = 0,
        title = "",
        typeOfEstate = "",
        typeOfOffer = "",
        etage = "",
        address = "",
        zipCode = "",
        city = "",
        description = "",
        addDate = 0,
        sellDate = 0,
        agent = "",
        sellingPrice = null,
        rent = null,
        surface = 0,
        nbRooms = 0,
        status = false,
        isFav = false,
        lat = null,
        lng = null
        ),
    val pictures: List<EstateMedia> = emptyList(),
    val interestPoints: List<EstateInterestPoints> = emptyList(),
)