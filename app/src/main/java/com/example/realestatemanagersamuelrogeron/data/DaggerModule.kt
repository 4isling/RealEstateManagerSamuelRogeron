package com.example.realestatemanagersamuelrogeron.data

import android.content.Context
import androidx.room.Room
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepository
import com.example.realestatemanagersamuelrogeron.data.repository.EstateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaggerModule {
    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDataBase::class.java,
        "real_estate_db"
    ).build()


    @Provides
    @Singleton
    fun provideEstateDao(db: AppDataBase): EstateDao {
        return db.getEstateDao()
    }

    @Provides
    @Singleton
    fun provideEstateRepository(dao: EstateDao): EstateRepository {
        return EstateRepositoryImpl(dao)
    }
}