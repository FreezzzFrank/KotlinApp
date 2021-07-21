package com.example.kotlinapp.sample.paging.data

import androidx.paging.PagingSource
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinapp.data.local.dao.BaseDao

interface CheeseDao : BaseDao<Cheese> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cheeses: List<Cheese>)

    @Delete
    fun delete(cheese: Cheese)

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByname(): PagingSource<Int, Cheese>
}