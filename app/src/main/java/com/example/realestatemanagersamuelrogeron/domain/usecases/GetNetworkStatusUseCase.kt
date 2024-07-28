package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.content.Context
import com.example.realestatemanagersamuelrogeron.utils.NetworkUtil.getNetworkStatusFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetNetworkStatusUseCase {
    suspend fun invoke(): Flow<Boolean>
}

class GetNetworkStatusUseCaseImpl @Inject constructor(
    private val context: Context
): GetNetworkStatusUseCase {
    override suspend fun invoke(): Flow<Boolean> {
        return getNetworkStatusFlow(context)
    }
}