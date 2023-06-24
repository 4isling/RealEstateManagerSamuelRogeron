package com.example.realestatemanagersamuelrogeron.data.event

import com.example.realestatemanagersamuelrogeron.domain.model.Estate

sealed interface DetailScreenEvent {
    data class DeleteEstate(val estate: Estate): DetailScreenEvent
    object OnClickModify: DetailScreenEvent
    object OnClickDelete: DetailScreenEvent


}