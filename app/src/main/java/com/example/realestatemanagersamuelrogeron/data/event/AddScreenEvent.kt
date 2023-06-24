package com.example.realestatemanagersamuelrogeron.data.event

import android.net.Uri
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures

sealed interface AddScreenEvent {
    object SaveEstate: AddScreenEvent
    data class SetTitle(val title: String): AddScreenEvent
    data class SetType(val type: String): AddScreenEvent
    data class SetOffer(val Offer: String): AddScreenEvent
    data class SetPrice(val Price: Int): AddScreenEvent
    data class SetRent(val Rent: Int): AddScreenEvent
    data class SetSurface(val Surface: Int): AddScreenEvent
    data class SetNbRoom(val NbRoom: Int): AddScreenEvent
    data class SetEtages(val Etages: String): AddScreenEvent
    data class SetDescriptions(val Descriptions: String): AddScreenEvent
    data class SetAddress(val Address: String): AddScreenEvent
    data class SetStatus(val Status: Boolean): AddScreenEvent
    data class SetAddDate(val AddDate: String): AddScreenEvent
    data class SetSellDate(val SellDate: String): AddScreenEvent
    data class SetAgent(val Agent: String): AddScreenEvent
    data class SetZipCode(val ZipCode: String): AddScreenEvent
    data class SetCity(val City: String): AddScreenEvent

    data class SetPicture(val Picture: List<Uri>): AddScreenEvent

    data class SetInterestPoint(val InterestPoint: List<String>): AddScreenEvent
}