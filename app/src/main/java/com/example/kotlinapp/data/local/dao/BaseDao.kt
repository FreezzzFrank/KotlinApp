package com.example.kotlinapp.data.local.dao

import androidx.room.Insert

interface BaseDao<T> {

    @Insert
    fun insert(vararg obj: T)


}