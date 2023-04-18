package com.example.realestatemanagersamuelrogeron.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.data.model.EstatePictures
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEstateViewModel @Inject constructor(
    private val estateRepository: EstateRepository
) : ViewModel() {
    private val _estate = MutableLiveData<Estate>()
    private val _estatePictures = MutableLiveData<List<EstatePictures>>()
    private val _estateInterestPoints = MutableLiveData<List<EstateInterestPoints>>()
    private val _estatePictureUri = MutableLiveData<List<Uri>>()

    val estateTitle = ""
    val estateType = ""
    val estateOffer = ""
    val estateSellingPrice = null
    val estateRent = null
    val estateSurface = 0
    val estateNbRooms = 0
    val estateEtages = ""
    val estateDescriptions = ""
    val estateAddress = ""
    val estateStatus = true
    val estateAddDate = ""
    val estateSellDate = ""
    val estateAgent = ""
    val estateZipCode = ""
    val estateCity = ""
    val estateUri = ""
    val estateUrl = ""
    val estatePictures = listOf<EstatePictures>()
    val estateInterestPoints = listOf<EstateInterestPoints>()
    val estateInterestPointsString = ""

    fun saveNewEstate(){
        if(validate()){
            addEstate()
        }
    }

    fun addEstate() {
        viewModelScope.launch {
            val id =
                estateRepository.addEstate(
                    Estate(
                        0,
                        estateTitle,
                        estateType,
                        estateOffer,
                        estateSellingPrice,
                        estateRent,
                        estateSurface,
                        estateNbRooms,
                        estateEtages,
                        estateDescriptions,
                        estateAddress,
                        estateZipCode,
                        estateCity,
                        estateAddDate,
                        estateSellDate,
                    )
                )
            //list of pictures the user add to the estate
            estateRepository.addEstatePictures(
                estatePictures = estatePictures
            )
            //list of interest points user add to the estate
            estateRepository.addEstateInterestPoints(
                estateInterestPoints = estateInterestPoints
            )
        }
    }

    fun addPicsUri(uri: Uri): MutableList<Uri>{
        val mUri = mutableListOf<Uri>()
        viewModelScope.launch {
            mUri.add(uri)
        }
        return mUri
    }

    fun addEstatePicture() {

    }

    fun validate(): Boolean =
        estateTitle.isNotEmpty() &&
                estateType.isNotEmpty() &&
                estateOffer.isNotEmpty() &&
                estateSurface > 0 &&
                estateNbRooms > 0 &&
                estateEtages.isNotEmpty() &&
                estateDescriptions.isNotEmpty() &&
                estateAddress.isNotEmpty() &&
                estateAddDate.isNotEmpty() &&
                estateAgent.isNotEmpty()
}