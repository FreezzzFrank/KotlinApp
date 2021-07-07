package com.example.kotlinapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.kotlinapp.data.local.converters.Converters
import com.example.kotlinapp.data.local.dao.GardenPlantingDao
import com.example.kotlinapp.data.local.dao.PlantDao
import com.example.kotlinapp.data.local.dao.UserInfoDao
import com.example.kotlinapp.data.local.entities.GardenPlanting
import com.example.kotlinapp.data.local.entities.Plant
import com.example.kotlinapp.data.local.entities.UserInfo
import com.example.kotlinapp.utilities.DATABASE_NAME
import com.example.kotlinapp.woker.SeedDatabaseWorker

/**
 * ---------------------------------------------------------
 * <h></h>
 * <p></p>
 * Created by Frank on 6/15/21.
 * <a href="mailto:frankyao10110@gmail.com">Contact me</a>
 * ---------------------------------------------------------
 */
@Database(entities = [UserInfo::class, Plant::class, GardenPlanting::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userInfoDao() : UserInfoDao
    abstract fun plantDao() : PlantDao
    abstract fun gardenPlantingDao() : GardenPlantingDao

    companion object {
        // For Singleton instantiation
        @Volatile private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                ).build()
        }
    }
}