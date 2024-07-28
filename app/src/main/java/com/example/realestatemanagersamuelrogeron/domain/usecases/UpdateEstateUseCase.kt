package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UpdateEstateUseCase {
    suspend fun invoke(estate: Estate)
}

class UpdateEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : UpdateEstateUseCase {
    override suspend fun invoke(estate: Estate) {
        withContext(Dispatchers.IO) {
            estateRepository.updateEstate(
                estate
            )
        }
    }
}
