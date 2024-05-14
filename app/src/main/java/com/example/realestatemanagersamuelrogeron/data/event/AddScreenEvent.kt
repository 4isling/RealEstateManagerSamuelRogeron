package com.example.realestatemanagersamuelrogeron.data.event

import android.net.Uri

sealed interface AddScreenEvent {
    object SaveEstate: AddScreenEvent
    data class SetTitle(val title: String): AddScreenEvent
    data class SetType(val type: String): AddScreenEvent
    data class SetOffer(val offer: String): AddScreenEvent
    data class SetPrice(val price: Int): AddScreenEvent
    data class SetRent(val rent: Int): AddScreenEvent
    data class SetSurface(val surface: Int): AddScreenEvent
    data class SetNbRoom(val nbRoom: Int): AddScreenEvent
    data class SetEtages(val etages: String): AddScreenEvent
    data class SetDescriptions(val descriptions: String): AddScreenEvent
    data class SetAddress(val address: String): AddScreenEvent
    data class SetStatus(val Status: Boolean): AddScreenEvent
    data class SetAddDate(val AddDate: String): AddScreenEvent
    data class SetSellDate(val SellDate: String): AddScreenEvent
    data class SetAgent(val Agent: String): AddScreenEvent
    data class SetZipCode(val ZipCode: String): AddScreenEvent
    data class SetCity(val City: String): AddScreenEvent
    data class SetPicture(val Picture: List<Uri>): AddScreenEvent
    data class SetInterestPoint(val interestPoint: List<String>): AddScreenEvent
}