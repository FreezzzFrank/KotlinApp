package com.example.kotlinapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinapp.data.local.entities.UserInfo
import kotlinx.coroutines.flow.Flow

/**
 * ---------------------------------------------------------
 * <h></h>
 * <p></p>
 * Created by Frank on 6/15/21.
 * <a href="mailto:frankyao10110@gmail.com">Contact me</a>
 * ---------------------------------------------------------
 */
@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_info WHERE user_id = :userId")
    fun getUserInfo(userId: String) : Flow<UserInfo>

    @Query("SELECT * FROM user_info WHERE mobile_no = :mobile")
    fun getUserInfoWithMobile(mobile: String) : Flow<UserInfo>

    @Query("SELECT * FROM user_info ORDER BY user_name")
    fun getUserInfos() : Flow<List<UserInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserInfo>)
}