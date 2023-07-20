package com.example.realestatemanagersamuelrogeron.domain.usecases

import android.util.Log
import com.example.realestatemanagersamuelrogeron.SortType
import com.example.realestatemanagersamuelrogeron.data.relations.PicturesWithEstate
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateWithPictures
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

interface GetAllEstatesWithPicturesUseCase {
    suspend fun execute(sortType: SortType): Flow<List<EstateWithPictures>>
}

class GetAllEstatesWithPicturesUseCaseImpl @Inject constructor(private val estateRepository: EstateRepository) :
    GetAllEstatesWithPicturesUseCase {
    override suspend fun execute(sortType: SortType): Flow<List<EstateWithPictures>> {
        val TAG = "GetEstateWithPictures"
        var estateList: Flow<List<EstateWithPictures>>
        try {
            when (sortType) {
                SortType.Default -> {
                    estateList = estateRepository.getAllEstatesWithPictures()
                    Log.i(TAG, "invoke: SortTypeDefault: $estateList")
                }

                SortType.PriceGrow -> {
                    estateList = estateRepository.getEstateWithPictureOrderedByGrowPrice()
                    Log.i(TAG, "invoke: SortTypePriceGrow: $estateList")
                }

                SortType.PriceDescend -> {
                    estateList = estateRepository.getAllEstatesWithPictureOrderedByDecendPrice()
                    Log.i(TAG, "invoke: SortTypePriceDescend: $estateList")
                }

                SortType.RentGrow -> {
                    estateList = estateRepository.getAllEstatesWithPictureOrderedByGrowRent()
                    Log.i(TAG, "invoke: SortTypeRentGrow:$estateList")
                }

                SortType.RentDescend -> {
                    estateList = estateRepository.getAllEstatesWithPictureOrderedByDecendRent()
                    Log.i(TAG, "invoke: SortTypeRentDescend: $estateList")
                }
            }
            return estateList
        } catch(e: Exception){
            return flow { emptyList<Estate>() }
        }
    }
}