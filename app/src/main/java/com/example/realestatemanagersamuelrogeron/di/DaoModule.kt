package com.example.realestatemanagersamuelrogeron.di

import android.content.Context
import android.location.Geocoder
import androidx.room.Room
import com.example.realestatemanagersamuelrogeron.data.AppDataBase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    fun provideEstateDao(
        @ApplicationContext context: Context
    ) : EstateDao {
        val db = Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            "database"
        ).build()
        return db.dao
    }

    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }
}