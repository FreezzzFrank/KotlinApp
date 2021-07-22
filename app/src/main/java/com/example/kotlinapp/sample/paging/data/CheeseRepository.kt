package com.example.kotlinapp.sample.paging.data

import com.example.kotlinapp.utilities.AppExecutors
import javax.inject.Inject

class CheeseRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val cheeseDao: CheeseDao
) {
    fun allCheeses() = cheeseDao.allCheesesByname()

    fun insert(text: CharSequence) = appExecutors.diskIO().execute {
        cheeseDao.insert(Cheese(0, text.toString()))
    }

    fun remove(cheese: Cheese) = appExecutors.diskIO().execute {
        cheeseDao.delete(cheese)
    }
}