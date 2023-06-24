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

    private val _viewState = MutableStateFlow<AddViewState>(AddViewState.Loading)
    val state = MutableStateFlow(AddEstateState())
    private lateinit var _estate: Estate
    private val _estateTitle = MutableStateFlow<String>("")
    private val _estateType = MutableStateFlow<String>("")
    private val _estateOffer = MutableStateFlow<String>("")
    private val _estateSellingPrice = MutableStateFlow<String>("")
    private val _estateRent = MutableStateFlow<String>("")
    private val _estateSurface = MutableStateFlow("")
    private val _estateNbRooms = MutableStateFlow("0")
    private val _estateEtages = MutableStateFlow("")
    private val _estateDescriptions = MutableStateFlow("")
    private val _estateAddress = MutableStateFlow("")
    private val _estateStatus = true
    private val _estateAddDate = Calendar.getInstance().time.toString()
    private val _estateSellDate = ""
    private val _estateAgent = "Steph"
    private val _estateZipCode = MutableStateFlow("")
    private val _estateCity = MutableStateFlow("")
    lateinit var estatePicture: EstatePictures
    lateinit var estateInterestPoint: EstateInterestPoints
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
    val estateNbRooms: MutableStateFlow<String> get() = _estateNbRooms
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
        _estateNbRooms.value != "0"
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
    val enableSave = derivedStateOf {
        when (_estateOffer.value) {
            "Rent" -> {
                titleIsNotEmpty
                typeIsNotEmpty
                offerIsNotEmpty
                rentIsNotEmpty
                surfaceIsNotEmpty
                roomIsNotEmpty
                etageIsNotEmpty
                descriptionsIsNotEmpty
                addressIsNotEmpty
                zipCodeIsNotEmpty
                cityIsNotEmpty
                onInterestPointsIsNotEmpty
            }

            "Sell" -> {
                titleIsNotEmpty
                typeIsNotEmpty
                offerIsNotEmpty
                sellingPriceIsNotEmpty
                surfaceIsNotEmpty
                roomIsNotEmpty
                etageIsNotEmpty
                descriptionsIsNotEmpty
                addressIsNotEmpty
                zipCodeIsNotEmpty
                cityIsNotEmpty
                onInterestPointsIsNotEmpty
            }

            "Rent or Sell" -> {
                titleIsNotEmpty
                typeIsNotEmpty
                offerIsNotEmpty
                sellingPriceIsNotEmpty
                rentIsNotEmpty
                surfaceIsNotEmpty
                roomIsNotEmpty
                etageIsNotEmpty
                descriptionsIsNotEmpty
                addressIsNotEmpty
                zipCodeIsNotEmpty
                cityIsNotEmpty
                onInterestPointsIsNotEmpty
            }

            else -> {false}
        }



    }


    fun onTitleValueChange(newValue: String) {
        _estateTitle.value = newValue
        println("addEstateViewModel: titleValueChange:$newValue")
    }

    fun onTypeValueChange(newValue: String) {
        _estateType.value = newValue
        println("addEstateViewModel: typeValueChange: $newValue")
    }

    fun onOfferValueChange(newValue: String) {
        _estateOffer.value = newValue
        println("addEstateViewModel: offerValueChange: $newValue")
    }

    fun onSellingPriceValueChange(newValue: String) {
        _estateSellingPrice.value = newValue
        println("addEstateViewModel: sellValueChange: $newValue")
    }

    fun onRentValueChange(newValue: String) {
        _estateRent.value = newValue
        println("addEstateViewModel: rentValueChange: $newValue")
    }

    fun onSurfaceValueChange(newValue: String) {
        _estateSurface.value = newValue
        println("addEstateViewModel: surfaceValueChange: $newValue")
    }

    fun onRoomValueChange(newValue: String) {
        _estateNbRooms.value = newValue
    }

    fun onEtageValueChange(newValue: String) {
        _estateEtages.value = newValue
    }

    fun onDescriptionValueChange(newValue: String) {
        _estateDescriptions.value = newValue
    }

    fun onAddressValueChange(newValue: String) {
        _estateAddress.value = newValue
    }

    fun onZipCodeValueChange(newValue: String) {
        _estateZipCode.value = newValue
    }

    fun onCityValueChange(newValue: String) {
        _estateCity.value = newValue
    }

    fun onPicturesValueChange(newValue: List<Uri>) {
        _estatePictures.value = newValue
        println("addEstateViewModel: picValueChange: ${newValue.size}pics")
    }

    fun onInterestPointsValueChange(newValue: List<String>) {
        _estateInterestPointsStrings.value = newValue
        println("addEstateViewModel: interestPointsValueChange: $newValue")
    }

    suspend fun addEstateSus(estate: Estate) {
        withContext(Dispatchers.IO) {
            println("addEstateSus")
            AddEstateUseCaseImpl.invoke(
                estate,
                interestPoints = _estateInterestPointsStrings.value,
                pics = _estatePictures.value
            )
                .catch { exception ->
                    _viewState.emit(AddViewState.Error(exception.message ?: "Unknown error"))
                }
                .collectLatest { estateId ->
                    println("addEstateSus:$estateId")
                }
        }

    }

    private fun addEstate(estate: Estate) = viewModelScope.launch {
        println("addEstateViewModel: addEstate$estate")
        addEstateSus(estate)
    }

    fun onSaveButtonClick() {
        when (_estateOffer.value) {
            "Rent" -> {
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
                    city = _estateCity.value,
                )
            }

            "Sell" -> {
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
            }

            "Rent or Sell" -> {
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
            }
        }
        println("AddEstateViewModel: onSaveButtonClick:$_estate")
        addEstate(_estate)
    }

    fun showBlankValue() {
        //TODO
    }

}