package com.example.realestatemanagersamuelrogeron.data.event

import com.example.realestatemanagersamuelrogeron.domain.model.Estate

sealed interface ListScreenEvent {
    object onClickEstateItem: ListScreenEvent
    object onClickFilterIcon: ListScreenEvent
    object onClickAddEstate: ListScreenEvent
    data class setEstate(val estate: Estate): ListScreenEvent
}