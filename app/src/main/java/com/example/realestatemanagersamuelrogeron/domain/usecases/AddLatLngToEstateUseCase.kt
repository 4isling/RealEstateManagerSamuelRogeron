package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.content.ContentValues.TAG
import android.location.Geocoder
import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import javax.inject.Inject

interface AddLatLngToEstatesUseCase {
    suspend fun invoke(estateId: Long):Boolean
}

class AddLatLngToEstatesUseCaseImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val estateRepository: EstateRepository
) : AddLatLngToEstatesUseCase {

    override suspend fun invoke(estateId: Long):Boolean {
            val estates = estateRepository.getEstateWithoutLatLng()
        return try {
            estates.collect {
                it.forEach {estate ->
                    val address =
                        estate.address // Replace with the appropriate property in your Estate class
                    val (latitude, longitude) = fetchLatLngFromGeocoder(address)
                    estateRepository.updateEstateLatLng(estateId, latitude, longitude)
                }
            }
            true
        }catch (e: Exception){
            Log.e(TAG, "invoke: $e", )
            false
        }
    }
    private suspend fun fetchLatLngFromGeocoder(address: String): Pair<Double, Double> {
        val result = geocoder.getFromLocationName(address, 1)
        if (result != null) {
            if (result.isNotEmpty()) {
                val latitude = result[0].latitude
                val longitude = result[0].longitude
                return Pair(latitude, longitude)
            }
        }
        throw Exception("Geocoder Failed to get latitude and longitude for the address: $address")
    }
}