package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface AddLatLngToEstatesUseCase {
    suspend fun invoke(): Boolean
}

class AddLatLngToEstatesUseCaseImpl @Inject constructor(
    private val geocoder: Geocoder,
    private val estateRepository: EstateRepository
) : AddLatLngToEstatesUseCase {
    companion object {
        private const val TAG = "AddLatLngToEstates"
    }

    override suspend fun invoke(): Boolean = withContext(Dispatchers.IO) {
        val estates = estateRepository.getEstateWithoutLatLng()
        return@withContext try {
            estates.collect { estateList ->
                estateList.forEach { estate ->
                    val address = estate.address + " ," + estate.zipCode + " " + estate.city + " ," + estate.region + " "+ estate.country// Replace with the appropriate property in your Estate class
                    val (latitude, longitude) = fetchLatLngFromGeocoder(address)
                    estateRepository.updateEstateLatLng(estate.estateId, latitude, longitude)
                }
            }
            true
        } catch (e: Exception) {
            Log.e(TAG, "invoke: $e")
            false
        }
    }

    private suspend fun fetchLatLngFromGeocoder(address: String): Pair<Double, Double> {
        return suspendCancellableCoroutine { continuation ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(address, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        if (addresses.isNotEmpty()) {
                            val latitude = addresses[0].latitude
                            val longitude = addresses[0].longitude
                            continuation.resume(Pair(latitude, longitude))
                        } else {
                            continuation.resumeWithException(Exception("Geocoder failed to get latitude and longitude for the address: $address"))
                        }
                    }

                    override fun onError(errorMessage: String?) {
                        continuation.resumeWithException(Exception("Geocoder error: $errorMessage"))
                    }
                })
            }else {
                // Fallback for SDK versions before 33
                val addresses = geocoder.getFromLocationName(address, 1)
                if (addresses != null) {
                    if (addresses.isNotEmpty()) {
                        val latitude = addresses[0].latitude
                        val longitude = addresses[0].longitude
                        continuation.resume(Pair(latitude, longitude))
                    } else {
                        continuation.resumeWithException(Exception("Geocoder failed to get latitude and longitude for the address: $address"))
                    }
                }
            }
        }
    }
}

