package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface GetUserLocationUseCase {
    suspend fun invoke(): Flow<Location>
}

class GetUserLocationUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GetUserLocationUseCase {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override suspend fun invoke(): Flow<Location> = callbackFlow {
        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val location = fusedLocationClient.lastLocation.await()

                if (location != null) {
                    trySend(location) // Send the location to the Flow
                } else {
                    close(Throwable("Location is null"))
                }
            } catch (e: Exception) {
                close(e) // Close the Flow with an exception if something goes wrong
            }
        } else {
            // Permission not granted, handle this case
            close(Throwable("Location permission not granted"))
        }

        awaitClose { /* Clean up resources if needed */ }
    }
}
