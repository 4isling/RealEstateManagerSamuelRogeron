package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject
import kotlin.math.log

interface GetEstateListUseCase {
    suspend fun invoke(sortType: SortType): Flow<List<Estate>>
}

class GetEstateListUseCaseImpl @Inject constructor(
    private val estateRepository: EstateRepository
) : GetEstateListUseCase {
    override suspend fun invoke(sortType: SortType): Flow<List<Estate>> = flow {
        val TAG = "getestateUseCAse"
        var estateList: Flow<List<Estate>>
        try {
            when (sortType) {
                SortType.Default -> {
                    estateList = estateRepository.getAllEstates()
                    Log.i(TAG, "invoke: SortTypeDefault: $estateList")
                }

                SortType.PriceGrow -> {
                    estateList = estateRepository.getAllEstatesOrderedByGrowPrice()
                    Log.i(TAG, "invoke: SortTypePriceGrow: $estateList")
                }

                SortType.PriceDescend -> {
                    estateList = estateRepository.getAllEstatesOrderedByDecendPrice()
                    Log.i(TAG, "invoke: SortTypePriceDescend: $estateList")
                }

                SortType.RentGrow -> {
                    estateList = estateRepository.getAllEstatesOrderedByGrowRent()
                    Log.i(TAG, "invoke: SortTypeRentGrow:$estateList")
                }

                SortType.RentDescend -> {
                    estateList = estateRepository.getAllEstatesOrderedByDecendRent()
                    Log.i(TAG, "invoke: SortTypeRentDescend: $estateList")
                }
            }
            emitAll(estateList.map {
                it
            })
        } catch(e: Exception){
            emit(emptyList())
        }
    }
}

