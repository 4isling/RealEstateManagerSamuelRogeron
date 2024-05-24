package com.example.realestatemanagersamuelrogeron.ui.add_screen.viewmodel

import android.net.Uri
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints

public final data class AddEstateState(
    val estates: List<Estate> = emptyList(),
    val estate: Estate? = null,
    val estateError: Boolean = false,
    val title: String = "",
    val titleError: Boolean = false,
    val type: String = "",
    val typeError:Boolean = false,
    val offer: String = "",
    val offerError: Boolean = false,
    val sellingPrice: String = "",
    val sellingPriceError: Boolean = false,
    val rent: String = "",
    val rentError: Boolean = false,
    val surface: String = "",
    val surfaceError: Boolean = false,
    val nbRooms: String = "",
    val nbRoomsError: Boolean = false,
    val etages: String = "",
    val etagesError: Boolean = false,
    val address: String = "",
    val addressError: Boolean = false,
    val zipCode: String = "",
    val zipCodeError: Boolean = false,
    val city: String = "",
    val cityError: Boolean = false,
    val description: String = "",
    val descriptionError: Boolean = false,
    val status: Boolean = true,
    val mediaSelected: List<Uri> = emptyList(),
    val mediaError: Boolean = false,
    val interestPointsStrings: List<String> = emptyList(),
    val interestPointError: Boolean = false,
    val addDate: String = "",
    val sellDate: String = "",
    val isFav: Boolean = false,
    val agent: String = "",
    val mediaFromCamera: List<Uri> = emptyList(),
    val interestPoints: List<EstateInterestPoints> = emptyList(),
    val selectedInterestPoints: List<EstateInterestPoints> = emptyList(),

    val isSaved: Boolean = false
)