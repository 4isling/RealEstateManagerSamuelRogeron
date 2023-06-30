package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.data.state.AddEstateState
import com.example.realestatemanagersamuelrogeron.data.state.AddViewState
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointsToEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddPicsToEstateUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddEstateViewModel @Inject constructor(
    private val AddEstateUseCaseImpl: AddEstateUseCaseImpl,
    private val AddInterestPoint: AddInterestPointsToEstateUseCaseImpl,
    private val AddPicsToEstateUseCase: AddPicsToEstateUseCaseImpl
) : ViewModel() {
    private val TAG = "AddEstateViewModel"
    private val _viewState = MutableStateFlow<AddViewState>(AddViewState.Loading)
    val state = MutableStateFlow(AddEstateState())
    private lateinit var _estate: Estate
    private val _estateTitle = MutableStateFlow<String>("")
    private val _estateType = MutableStateFlow<String>("")
    private val _estateOffer = MutableStateFlow<String>("")
    private val _estateSellingPrice = MutableStateFlow<String>("")
    private val _estateRent = MutableStateFlow<String>("")
    private val _estateSurface = MutableStateFlow("")
    private val _estateNbRooms = MutableStateFlow(0)
    private val _estateEtages = MutableStateFlow("")
    private val _estateDescriptions = MutableStateFlow("")
    private val _estateAddress = MutableStateFlow("")
    private val _estateStatus = true
    private val _estateAddDate = Calendar.getInstance().time.toString()
    private val _estateSellDate = ""
    private val _estateAgent = "Steph"
    private val _estateZipCode = MutableStateFlow("")
    private val _estateCity = MutableStateFlow("")
    private val _estatePictures = MutableStateFlow(emptyList<Uri>())
    private val _estateInterestPointsStrings = MutableStateFlow(emptyList<String>())
    private val _estatePicturesList = MutableStateFlow<List<EstatePictures>>(emptyList())
    private val _estateInterestPoints = MutableStateFlow<List<EstateInterestPoints>>(emptyList())
    val estateTitle: MutableStateFlow<String> get() = _estateTitle
    val estateType: MutableStateFlow<String> get() = _estateType
    val estateOffer: MutableStateFlow<String> get() = _estateOffer
    val estateSellingPrice: MutableStateFlow<String> get() = _estateSellingPrice
    val estateRent: MutableStateFlow<String> get() = _estateRent
    val estateSurface: MutableStateFlow<String> get() = _estateSurface
    val estateNbRooms: MutableStateFlow<Int> get() = _estateNbRooms
    val estateEtages: MutableStateFlow<String> get() = _estateEtages
    val estateDescriptions: MutableStateFlow<String> get() = _estateDescriptions
    val estateAddress: MutableStateFlow<String> get() = _estateAddress
    val estateZipCode: MutableStateFlow<String> get() = _estateZipCode
    val estateCity: MutableStateFlow<String> get() = _estateCity
    val estateInterestPointsStrings: MutableStateFlow<List<String>> get() = _estateInterestPointsStrings
    val estatePictures: MutableStateFlow<List<Uri>> get() = _estatePictures

    val titleIsNotEmpty = derivedStateOf {
        _estateTitle.value.isNotBlank()
    }
    val typeIsNotEmpty = derivedStateOf {
        _estateType.value.isNotBlank()
    }
    val offerIsNotEmpty = derivedStateOf {
        _estateOffer.value.isNotBlank()
    }
    val sellingPriceIsNotEmpty = derivedStateOf {
        _estateSellingPrice.value.isNotBlank()
    }
    val rentIsNotEmpty = derivedStateOf {
        _estateRent.value.isNotEmpty()
    }
    val surfaceIsNotEmpty = derivedStateOf {
        _estateSurface.value.isNotEmpty()
    }
    val roomIsNotEmpty = derivedStateOf {
        _estateNbRooms.value != 0
    }
    val etageIsNotEmpty = derivedStateOf {
        _estateEtages.value.isNotBlank()
    }
    val descriptionsIsNotEmpty = derivedStateOf {
        _estateDescriptions.value.isNotBlank()
    }
    val addressIsNotEmpty = derivedStateOf {
        _estateAddress.value.isNotBlank()
    }

    val zipCodeIsNotEmpty = derivedStateOf {
        _estateZipCode.value.isNotBlank()
    }
    val cityIsNotEmpty = derivedStateOf {
        _estateCity.value.isNotBlank()
    }
    val onInterestPointsIsNotEmpty = derivedStateOf {
        _estateInterestPointsStrings.value.isNotEmpty()
    }

    fun enableSave(): Boolean {

        Log.i(TAG, "enableSave:")
        when (_estateOffer.value) {
            "Rent" -> {
                Log.i(TAG, "enableSave: Rent " +
                        "title:${titleIsNotEmpty.value} "+
                        "type: ${typeIsNotEmpty.value} " +
                        "offer: ${offerIsNotEmpty.value} " +
                        "rent:${rentIsNotEmpty.value} " +
                        "surface:${surfaceIsNotEmpty.value} " +
                        "room: ${roomIsNotEmpty.value} " +
                        "etage: ${etageIsNotEmpty.value} " +
                        "description: ${descriptionsIsNotEmpty.value} " +
                        "address: ${addressIsNotEmpty.value} " +
                        "zipCode: ${zipCodeIsNotEmpty.value} " +
                        "city: ${cityIsNotEmpty.value} " +
                        "Ipts: ${onInterestPointsIsNotEmpty.value} ")
                return (
                        titleIsNotEmpty.value &&
                        typeIsNotEmpty.value &&
                        offerIsNotEmpty.value &&
                        rentIsNotEmpty.value &&
                        surfaceIsNotEmpty.value &&
                        roomIsNotEmpty.value &&
                        etageIsNotEmpty.value &&
                        descriptionsIsNotEmpty.value &&
                        addressIsNotEmpty.value &&
                        zipCodeIsNotEmpty.value &&
                        cityIsNotEmpty.value &&
                        onInterestPointsIsNotEmpty.value
                        )

            }

            "Sell" -> {
                Log.i(TAG, "enableSave: Sell"+
                        "title:${titleIsNotEmpty.value} "+
                        "type: ${typeIsNotEmpty.value} " +
                        "offer: ${offerIsNotEmpty.value} " +
                        "sell:${sellingPriceIsNotEmpty.value} " +
                        "surface:${surfaceIsNotEmpty.value} " +
                        "room: ${roomIsNotEmpty.value} " +
                        "etage: ${etageIsNotEmpty.value} " +
                        "description: ${descriptionsIsNotEmpty.value} " +
                        "address: ${addressIsNotEmpty.value} " +
                        "zipCode: ${zipCodeIsNotEmpty.value} " +
                        "city: ${cityIsNotEmpty.value} " +
                        "Ipts: ${onInterestPointsIsNotEmpty.value} ")
                return (
                        titleIsNotEmpty.value &&
                         typeIsNotEmpty.value &&
                         offerIsNotEmpty.value &&
                         sellingPriceIsNotEmpty.value &&
                         surfaceIsNotEmpty.value &&
                         roomIsNotEmpty.value &&
                         etageIsNotEmpty.value &&
                         descriptionsIsNotEmpty.value &&
                         addressIsNotEmpty.value &&
                         zipCodeIsNotEmpty.value &&
                         cityIsNotEmpty.value &&
                         onInterestPointsIsNotEmpty.value
                        )
            }

            "Rent or Sell" -> {
                Log.i(TAG, "enableSave: Rent or Sell"+
                        "title:${titleIsNotEmpty.value} "+
                        "type: ${typeIsNotEmpty.value} " +
                        "offer: ${offerIsNotEmpty.value} " +
                        "rent:${rentIsNotEmpty.value} " +
                        "sell:${sellingPriceIsNotEmpty.value} " +
                        "surface:${surfaceIsNotEmpty.value} " +
                        "room: ${roomIsNotEmpty.value} " +
                        "etage: ${etageIsNotEmpty.value} " +
                        "description: ${descriptionsIsNotEmpty.value} " +
                        "address: ${addressIsNotEmpty.value} " +
                        "zipCode: ${zipCodeIsNotEmpty.value} " +
                        "city: ${cityIsNotEmpty.value} " +
                        "Ipts: ${onInterestPointsIsNotEmpty.value} ")
                return (
                        titleIsNotEmpty.value &&
                                typeIsNotEmpty.value &&
                                offerIsNotEmpty.value &&
                                sellingPriceIsNotEmpty.value &&
                                rentIsNotEmpty.value &&
                                surfaceIsNotEmpty.value &&
                                roomIsNotEmpty.value &&
                                etageIsNotEmpty.value &&
                                descriptionsIsNotEmpty.value &&
                                addressIsNotEmpty.value &&
                                zipCodeIsNotEmpty.value &&
                                cityIsNotEmpty.value &&
                                onInterestPointsIsNotEmpty.value)
            }

            else -> {
                Log.i(TAG,"enableSave: else"+
                        "title:${titleIsNotEmpty.value} "+
                        "type: ${typeIsNotEmpty.value} " +
                        "offer: ${offerIsNotEmpty.value} " +
                        "rent:${rentIsNotEmpty.value} " +
                        "sell:${sellingPriceIsNotEmpty.value} " +
                        "surface:${surfaceIsNotEmpty.value} " +
                        "room: ${roomIsNotEmpty.value} " +
                        "etage: ${etageIsNotEmpty.value} " +
                        "description: ${descriptionsIsNotEmpty.value} " +
                        "address: ${addressIsNotEmpty.value} " +
                        "zipCode: ${zipCodeIsNotEmpty.value} " +
                        "city: ${cityIsNotEmpty.value} " +
                        "Ipts: ${onInterestPointsIsNotEmpty.value} ")
                return false
            }
        }
    }


    fun onTitleValueChange(newValue: String) {
        _estateTitle.value = newValue
        Log.i(TAG, "titleValueChange:${_estateTitle.value}")
    }

    fun onTypeValueChange(newValue: String) {
        _estateType.value = newValue
        Log.i(TAG, "typeValueChange: ${_estateType.value}")
    }

    fun onOfferValueChange(newValue: String) {
        _estateOffer.value = newValue

        Log.i(TAG, "offerValueChange: ${_estateOffer.value}")
    }

    fun onSellingPriceValueChange(newValue: String) {
        _estateSellingPrice.value = newValue
        Log.i(TAG, "sellValueChange: ${_estateSellingPrice.value}")
    }

    fun onRentValueChange(newValue: String) {
        _estateRent.value = newValue
        Log.i(TAG, "rentValueChange: ${_estateRent.value}")
    }

    fun onSurfaceValueChange(newValue: String) {
        _estateSurface.value = newValue
        Log.i(TAG, "surfaceValueChange: ${_estateSurface.value}")
    }

    fun onRoomValueChange(newValue: Int) {
        _estateNbRooms.value = newValue
        Log.i(TAG, "roomValueChange: ${_estateNbRooms.value}")
    }

    fun onEtageValueChange(newValue: String) {
        _estateEtages.value = newValue
        Log.i(TAG, "onEtageValueChange: ${_estateEtages.value}")
    }

    fun onDescriptionValueChange(newValue: String) {
        _estateDescriptions.value = newValue
        Log.i(TAG, "onDescriptionValueChange: ${_estateDescriptions.value}")
    }

    fun onAddressValueChange(newValue: String) {
        _estateAddress.value = newValue
        Log.i(TAG, "onAddressValueChange: ${_estateAddress.value}")
    }

    fun onZipCodeValueChange(newValue: String) {
        _estateZipCode.value = newValue
        Log.i(TAG, "onZipCodeValueChange: ${_estateZipCode.value}")
    }

    fun onCityValueChange(newValue: String) {
        _estateCity.value = newValue
        Log.i(TAG, "onCityValueChange: ${_estateCity.value}")
    }

    fun onPicturesValueChange(newValue: List<Uri>) {
        _estatePictures.value = newValue
        Log.i(TAG, "picValueChange: ${_estatePictures.value.size}pics")
    }

    fun onInterestPointsValueChange(newValue: List<String>) {
        _estateInterestPointsStrings.value = newValue
        Log.i(TAG, "interestPointsValueChange: ${_estateInterestPointsStrings.value}")
    }

    suspend fun addEstateSus(estate: Estate) {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "addEstateSus log 1")
            AddEstateUseCaseImpl.invoke(
                estate,
                interestPoints = _estateInterestPointsStrings.value,
                pics = _estatePictures.value
            )
                .catch { exception ->
                    _viewState.emit(AddViewState.Error(exception.message ?: "Unknown error"))
                }
                .collectLatest { estateId ->
                    Log.i(TAG, "addEstateSus:$estateId")
                }
        }

    }

    private fun addEstate(estate: Estate) = viewModelScope.launch {
        Log.i(TAG, "addEstate: $estate   call addEstateSus")
        addEstateSus(estate)
    }

    fun onSaveButtonClick() {
        if (enableSave()) {
            when (_estateOffer.value) {
                "Rent" -> {
                    Log.i(TAG, "onSaveButtonClick:Rent")
                    _estate = Estate(
                        title = _estateTitle.value,
                        typeOfEstate = _estateType.value,
                        typeOfOffer = _estateOffer.value,
                        rent = _estateRent.value.toInt(),
                        surface = _estateSurface.value.toInt(),
                        nbRooms = _estateNbRooms.value.toInt(),
                        etage = _estateEtages.value,
                        description = _estateDescriptions.value,
                        address = _estateAddress.value,
                        status = _estateStatus,
                        addDate = _estateAddDate,
                        sellDate = _estateSellDate,
                        agent = _estateAgent,
                        zipCode = _estateZipCode.value,
                        city = _estateCity.value,)
                    addEstate(_estate)
                }

                "Sell" -> {
                    Log.i(TAG, "onSaveButtonClick:Sell")
                    _estate = Estate(
                        title = _estateTitle.value,
                        typeOfEstate = _estateType.value,
                        typeOfOffer = _estateOffer.value,
                        sellingPrice = _estateSellingPrice.value.toInt(),
                        surface = _estateSurface.value.toInt(),
                        nbRooms = _estateNbRooms.value.toInt(),
                        etage = _estateEtages.value,
                        description = _estateDescriptions.value,
                        address = _estateAddress.value,
                        status = _estateStatus,
                        addDate = _estateAddDate,
                        sellDate = _estateSellDate,
                        agent = _estateAgent,
                        zipCode = _estateZipCode.value,
                        city = _estateCity.value,
                    )
                    addEstate(_estate)
                }

                "Rent or Sell" -> {
                    Log.i(TAG, "onSaveButtonClick:Rent or Sell")
                    _estate = Estate(
                        title = _estateTitle.value,
                        typeOfEstate = _estateType.value,
                        typeOfOffer = _estateOffer.value,
                        sellingPrice = _estateSellingPrice.value.toInt(),
                        rent = _estateRent.value.toInt(),
                        surface = _estateSurface.value.toInt(),
                        nbRooms = _estateNbRooms.value.toInt(),
                        etage = _estateEtages.value,
                        description = _estateDescriptions.value,
                        address = _estateAddress.value,
                        status = _estateStatus,
                        addDate = _estateAddDate,
                        sellDate = _estateSellDate,
                        agent = _estateAgent,
                        zipCode = _estateZipCode.value,
                        city = _estateCity.value,
                    )
                    addEstate(_estate)
                }
            }
        }
    }
    fun showBlankValue() {
        //TODO
    }

}