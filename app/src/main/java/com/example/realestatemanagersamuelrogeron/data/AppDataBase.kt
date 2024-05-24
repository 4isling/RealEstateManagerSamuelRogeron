package com.example.realestatemanagersamuelrogeron.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.realestatemanagersamuelrogeron.data.dao.EstateDao
import com.example.realestatemanagersamuelrogeron.data.relations.EstateInterestPointCrossRef
import com.example.realestatemanagersamuelrogeron.domain.model.Estate
import com.example.realestatemanagersamuelrogeron.domain.model.EstateInterestPoints
import com.example.realestatemanagersamuelrogeron.domain.model.EstateMedia

@Database(
    entities = [
        Estate::class,
        EstateInterestPoints::class,
        EstateMedia::class,
        EstateInterestPointCrossRef::class,
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun estateDao(): EstateDao

    companion object {
        private var INSTANCE: AppDataBase? = null
        fun getDatabase(context: Context): AppDataBase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context, AppDataBase::class.java, "database")
                        .createFromAsset("database/REM_database.db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}