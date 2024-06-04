package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

interface GetUserLocationUseCase {
    suspend fun invoke(): Flow<Location>
}

class GetUserLocationUseCaseImpl @Inject constructor(
    private val context: Context
) : GetUserLocationUseCase {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun invoke(): Flow<Location> = callbackFlow {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    {
                        /* {@TODO} **/
                    }
                }
        } else {
            // Permission not granted, handle this case
            close(Throwable("Location permission not granted"))
        }
    }
}
