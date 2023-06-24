package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.event.AddScreenEvent
import com.example.realestatemanagersamuelrogeron.data.event.DetailScreenEvent
import com.example.realestatemanagersamuelrogeron.data.event.DetailScreenEvent.DeleteEstate
import com.example.realestatemanagersamuelrogeron.data.event.ListScreenEvent
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.data.state.EstateState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class EstateViewModel (
    private val repository: EstateRepository,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel(){

        private val _sortType = MutableStateFlow(SortType.Default)
        private val _estates = _sortType
            .flatMapLatest { sortType ->
                when(sortType){
                    SortType.Default -> repository.getAllEstates()
                    SortType.PriceGrow -> repository.getAllEstatesOrderedByGrowPrice()
                    SortType.PriceDescend -> repository.getAllEstatesOrderedByDecendPrice()
                    SortType.RentGrow -> repository.getAllEstatesOrderedByGrowRent()
                    SortType.RentDescend -> repository.getAllEstatesOrderedByDecendRent()
                }
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(EstateState())

    val state = combine(_state, _sortType, _estates) { state, sortType, estates ->
        state.copy(
            estates = estates,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EstateState())


    fun onAddScreenEvent(event: AddScreenEvent) {
        when(event) {

            is AddScreenEvent.SaveEstate -> {
                val title = state.value.title
                val typeOfOffer = state.value.offer
                val etages = state.value.etages
                val address = state.value.address
                val zipCode = state.value.zipCode
                val city = state.value.city
                val descriptions = state.value.descriptions
                val addDate = state.value.addDate
                val sellDate = state.value.sellDate
                val agent = state.value.agent
                val price = state.value.sellingPrice
                val rent = state.value.rent
                val surface = state.value.surface
                val nbRoom = state.value.nbRooms
                val status = state.value.status
                val isFav = state.value.isFav
                if (title.isBlank() || address.isBlank() || zipCode.isBlank() || city.isBlank() || descriptions.isBlank() || surface == 0 || nbRoom.isBlank()) {
                    return
                }
            }
            is AddScreenEvent.SetAddDate -> _state.update {
                    it.copy(
                        addDate = event.AddDate
                )
            }
            is AddScreenEvent.SetAddress -> _state.update {
                it.copy(
                    address = event.Address
                )
            }
            is AddScreenEvent.SetAgent -> _state.update {
                it.copy(
                    agent = event.Agent
                )
            }
            is AddScreenEvent.SetCity -> _state.update {
                it.copy(
                    city = event.City
                )
            }
            is AddScreenEvent.SetDescriptions -> _state.update {
                it.copy(
                    descriptions = event.Descriptions
                )
            }
            is AddScreenEvent.SetEtages -> _state.update {
                it.copy(
                    etages = event.Etages
                )
            }
            is AddScreenEvent.SetInterestPoint -> _state.update {
                it.copy(
                    interestPointsStrings = event.InterestPoint
                )
            }
            is AddScreenEvent.SetNbRoom -> _state.update {
                it.copy(
                    nbRooms = event.NbRoom.toString()
                )
            }
            is AddScreenEvent.SetOffer -> _state.update {
                it.copy(
                    offer = event.Offer
                )
            }
            is AddScreenEvent.SetPicture -> _state.update {
                it.copy(
                    pictures = event.Picture
                )
            }
            is AddScreenEvent.SetPrice -> _state.update {
                it.copy(
                    sellingPrice = event.Price
                )
            }
            is AddScreenEvent.SetRent -> _state.update {
                it.copy(
                    rent = event.Rent
                )
            }
            is AddScreenEvent.SetSellDate -> _state.update {
                it.copy(
                    sellDate = event.SellDate
                )
            }
            is AddScreenEvent.SetStatus -> _state.update {
                it.copy(
                    status = event.Status
                )
            }
            is AddScreenEvent.SetSurface -> _state.update {
                it.copy(
                    surface = event.Surface
                )
            }
            is AddScreenEvent.SetTitle -> _state.update {
                it.copy(
                    title = event.title
                )
            }
            is AddScreenEvent.SetType -> _state.update {
                it.copy(
                    type = event.type
                )
            }
            is AddScreenEvent.SetZipCode -> _state.update {
                it.copy(
                    zipCode = event.ZipCode
                )
            }

        }
    }

    fun onDetailScreenEvent(event: DetailScreenEvent){
        when(event){
            DetailScreenEvent.OnClickDelete -> TODO()
            DetailScreenEvent.OnClickModify -> TODO()
            is DeleteEstate -> {
                viewModelScope.launch {
                    repository.delete(event.estate)
                }
            }
        }
    }

    fun onListScreenEvent(event: ListScreenEvent){
        when(event) {
            ListScreenEvent.onClickEstateItem -> {

            }
            ListScreenEvent.onClickFilterIcon -> TODO()
            ListScreenEvent.onClickAddEstate -> TODO()
            is ListScreenEvent.setEstate -> {
                _state.update {
                    it.copy(
                        estate = event.estate
                    )
                }
            }
        }
    }

}