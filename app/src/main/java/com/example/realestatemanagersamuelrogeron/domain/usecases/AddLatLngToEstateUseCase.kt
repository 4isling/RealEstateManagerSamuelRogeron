package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.location.Geocoder
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

interface AddLatLngToEstatesUseCase {
    suspend fun invoke(estateId: Long)
}

class AddLatLngToEstateUseCaseImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val estateRepository: EstateRepository
) : AddLatLngToEstatesUseCase {

    override suspend fun invoke(estateId: Long) {
        val estate = estateRepository.getEstateById(estateId).firstOrNull()
        if (estate != null && estate.lat == null && estate.lng == null) {
            val address = estate.address // Replace with the appropriate property in your Estate class
            val (latitude, longitude) = fetchLatLngFromGeocoder(address)
            estateRepository.updateEstateLatLng(estateId, latitude, longitude)
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