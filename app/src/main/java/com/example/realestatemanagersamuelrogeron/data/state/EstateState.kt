package com.example.realestatemanagersamuelrogeron.data.state

import android.net.Uri
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.data.event.AddScreenEvent
import com.example.realestatemanagersamuelrogeron.domain.model.Estate

public final data class EstateState(
    val estates: List<Estate> = emptyList(),
    val estate: Estate? = null,
    val title: String = "",
    val type: String = "",
    val offer: String = "",
    val sellingPrice: Int = 0,
    val rent: Int = 0,
    val surface: Int = 0,
    val nbRooms: String = "",
    val etages: String = "",
    val address: String = "",
    val zipCode: String = "",
    val city: String = "",
    val descriptions: String = "",
    val status: Boolean = true,
    val pictures: List<Uri> = emptyList(),
    val interestPointsStrings: List<String> = emptyList(),
    val addDate: String = "",
    val sellDate: String = "",
    val isFav: Boolean = false,
    val agent: String = "",
    val sortType: SortType = SortType.Default
)