package com.example.realestatemanagersamuelrogeron.di

import android.content.Context
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.AddInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.DeleteInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.DeleteInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.EditEstateUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.EditEstateUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllInterestPointUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetAllInterestPointUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetCurrencyPreferenceUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateInterestPointsUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetEstateInterestPointsUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetNetworkStatusUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetNetworkStatusUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetStaticMapUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetStaticMapUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetUserLocationUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.GetUserLocationUseCaseImpl
import com.example.realestatemanagersamuelrogeron.domain.usecases.SaveCurrencyPreferenceUseCase
import com.example.realestatemanagersamuelrogeron.domain.usecases.SaveCurrencyPreferenceUseCaseImpl
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

    @Provides
    @Singleton
    fun provideDeleteInterestPointUseCase(
        estateRepository: EstateRepository
    ):DeleteInterestPointUseCase {
        return DeleteInterestPointUseCaseImpl(estateRepository)
    }

    @Provides
    @Singleton
    fun provideGetEstateInterestPointsUseCase(
        estateRepository: EstateRepository
    ): GetEstateInterestPointsUseCase {
        return GetEstateInterestPointsUseCaseImpl(estateRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllInterestPointUseCase(
        estateRepository: EstateRepository
    ): GetAllInterestPointUseCase {
        return GetAllInterestPointUseCaseImpl(estateRepository)
    }

    @Provides
    @Singleton
    fun provideGetNetworkStatusUseCase(
        @ApplicationContext context: Context
    ): GetNetworkStatusUseCase {
        return GetNetworkStatusUseCaseImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideSaveCurrencyPreferenceUseCase(
        dataStorePreference: DataStorePreference
    ): SaveCurrencyPreferenceUseCase {
        return SaveCurrencyPreferenceUseCaseImpl(dataStorePreference = dataStorePreference)
    }

    @Provides
    @Singleton
    fun provideGetCurrencyPreferenceUseCase(
        dataStorePreference: DataStorePreference
    ): GetCurrencyPreferenceUseCase {
        return GetCurrencyPreferenceUseCaseImpl(dataStorePreference)
    }

    @Provides
    @Singleton
    fun provideGetStaticMapUseCase(
        getNetworkStatusUseCase: GetNetworkStatusUseCase
    ): GetStaticMapUseCase {
        return GetStaticMapUseCaseImpl(getNetworkStatusUseCase)
    }

    @Provides
    fun provideGetUserLocationUseCase(
        @ApplicationContext context: Context
    ): GetUserLocationUseCase {
        return GetUserLocationUseCaseImpl(context)
    }

    @Provides
    fun provideEditEstateUseCase(
        estateRepository: EstateRepository,
        @ApplicationContext context: Context
    ): EditEstateUseCase {
        return EditEstateUseCaseImpl(estateRepository, context)
    }
}