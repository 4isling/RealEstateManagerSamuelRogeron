package com.example.realestatemanagersamuelrogeron.domain.usecases

import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetEstateListUseCase {
    suspend fun invoke(sortType: SortType): Flow<List<Estate>>
}

class GetEstateListUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : GetEstateListUseCase {

    override suspend fun invoke(sortType: SortType): Flow<List<Estate>> {
        return try {
            flow {
                when (sortType) {
                    SortType.Default -> estateRepository.getAllEstates()
                    SortType.PriceGrow -> estateRepository.getAllEstatesOrderedByGrowPrice()
                    SortType.PriceDescend -> estateRepository.getAllEstatesOrderedByDecendPrice()
                    SortType.RentGrow -> estateRepository.getAllEstatesOrderedByGrowRent()
                    SortType.RentDescend -> estateRepository.getAllEstatesOrderedByDecendRent()
                }
            }
        } catch (e: Exception) {
            flow {
                emptyList<Estate>()
            }
        }


    }
}