package com.example.realestatemanagersamuelrogeron.di

import android.content.Context
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.utils.DataStorePreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideAddEstateUseCase(
        estateRepository: EstateRepository,
        @ApplicationContext context: Context
    ): AddEstateUseCase {
        return AddEstateUseCaseImpl(estateRepository = estateRepository, context = context)
    }

    @Provides
    fun provideAddInterestPointUseCase(
        estateRepository: EstateRepository
    ): AddInterestPointUseCase {
        return AddInterestPointUseCaseImpl(estateRepository)
    }

    @Provides
    @Singleton
    fun provideDataStorePreference(
        @ApplicationContext context: Context
    ): DataStorePreference {
        return DataStorePreference(context)
    }
}