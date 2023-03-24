package com.example.realestatemanagersamuelrogeron.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.model.Estate
import com.example.realestatemanagersamuelrogeron.data.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.data.model.EstatePictures

@Database(
    entities = [
        Estate::class,
        EstateInterestPoints::class,
        EstatePictures::class
    ],
    version = 1
)
abstract class AppDataBase: RoomDatabase() {
    abstract val estateDao: EstateDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this){

                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "App_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}