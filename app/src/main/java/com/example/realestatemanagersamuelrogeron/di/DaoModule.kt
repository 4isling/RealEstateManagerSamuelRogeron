package com.example.realestatemanagersamuelrogeron.di

import android.content.Context
import android.location.Geocoder
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDataBase {
        return AppDataBase.getDatabase(context)
    }

    @Provides
    fun provideEstateDao(
        database: AppDataBase
    ): EstateDao {
        return database.estateDao()
    }

    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }
}