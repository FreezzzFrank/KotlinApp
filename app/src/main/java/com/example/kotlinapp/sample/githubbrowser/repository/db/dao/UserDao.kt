package com.example.kotlinapp.sample.githubbrowser.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.kotlinapp.data.local.dao.BaseDao
import com.example.kotlinapp.sample.githubbrowser.repository.db.entities.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM user WHERE login = :login")
    fun findByLogin(login: String): LiveData<User>
}