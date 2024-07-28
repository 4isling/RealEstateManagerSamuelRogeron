package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.utils.StaticMapUrlProvider
import javax.inject.Inject

interface GetStaticMapUseCase {
    suspend fun invoke(
        entry: Estate,
    ): String
}

class GetStaticMapUseCaseImpl @Inject constructor(
    getNetworkStatusUseCase: GetNetworkStatusUseCase
): GetStaticMapUseCase {
    override suspend fun invoke(entry: Estate): String {
        return if (entry.lat != null && entry.lng != null){
            StaticMapUrlProvider.getStaticMapUrl(latitude = entry.lat, longitude = entry.lng)
        }else{
            "Error"
        }
    }

}