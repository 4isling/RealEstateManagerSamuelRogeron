package com.example.realestatemanagersamuelrogeron.di

import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAddEstateUseCase(
        estateRepository: EstateRepository
    ): AddEstateUseCase {
        return AddEstateUseCaseImpl(estateRepository)
    }

    @Provides
    fun provideAddInterestPointUseCase(
        estateRepository: EstateRepository
    ): AddInterestPointUseCase {
        return AddInterestPointUseCaseImpl(estateRepository)
    }
}