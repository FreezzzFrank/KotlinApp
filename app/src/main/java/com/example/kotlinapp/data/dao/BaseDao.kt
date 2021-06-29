package com.example.kotlinapp.data.dao

import androidx.room.Insert

interface BaseDao<T> {

    @Insert
    fun insert(vararg obj: T)


}