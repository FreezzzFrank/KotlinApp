package com.example.kotlinapp.woker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.kotlinapp.data.local.AppDataBase
import com.example.kotlinapp.data.local.entities.Plant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

import com.example.kotlinapp.utilities.PLANT_FILE_NAME
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import java.lang.Exception

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(PLANT_FILE_NAME).use { inputStream ->
                JsonReader(inputStream.reader()).use {
                    val plantType = object : TypeToken<List<Plant>>() {}.type
                    val plantList: List<Plant> = Gson().fromJson(it, plantType)

                    val database = AppDataBase.getInstance(applicationContext)
                    database.plantDao().insertAll(plantList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding database")
            Result.failure()
        }
    }
}