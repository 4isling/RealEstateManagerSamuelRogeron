package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import javax.inject.Inject

interface AddInterestPointUseCase {
    suspend fun invoke(interestPoint: EstateInterestPoints): Long
}

class AddInterestPointUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : AddInterestPointUseCase {
    override suspend fun invoke(interestPoint: EstateInterestPoints): Long {
        return estateRepository.addEstateInterestPoint(interestPoint)
    }
}