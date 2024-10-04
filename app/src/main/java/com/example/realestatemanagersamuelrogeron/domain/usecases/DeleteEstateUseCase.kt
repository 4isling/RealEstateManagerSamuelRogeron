package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import javax.inject.Inject

interface DeleteEstateUseCase {
    fun invoke(id: Long): Result<Unit>
}

class DeleteEstateUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : DeleteEstateUseCase {
    override fun invoke(id: Long): Result<Unit> {
        return try {
            estateRepository.deleteEstateById(id)
            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(DeleteEstateException("Failed to delete estate with id: $id", exception))
        }
    }
    class DeleteEstateException(message: String, cause: Throwable): RuntimeException(message, cause)
}