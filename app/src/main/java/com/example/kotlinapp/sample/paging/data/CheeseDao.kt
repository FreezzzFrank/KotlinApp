package com.example.kotlinapp.sample.paging.data

import androidx.paging.PagingSource
import androidx.room.*
import com.example.kotlinapp.data.local.dao.BaseDao

@Dao
interface CheeseDao : BaseDao<Cheese> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cheeses: List<Cheese>)

    @Delete
    fun delete(cheese: Cheese)

    @Query("SELECT * FROM Cheese ORDER BY name COLLATE NOCASE ASC")
    fun allCheesesByname(): PagingSource<Int, Cheese>
}