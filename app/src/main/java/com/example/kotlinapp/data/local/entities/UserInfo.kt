package com.example.kotlinapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * ---------------------------------------------------------
 * <h></h>
 * <p></p>
 * Created by Frank on 6/15/21.
 * <a href="mailto:frankyao10110@gmail.com">Contact me</a>
 * ---------------------------------------------------------
 */
@Entity(tableName = "user_info")
data class UserInfo(
    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "mobile_no")
    val mobile: String,

    @ColumnInfo(name = "user_name")
    val userName: String,

    val token: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userInfoId: Long = 0
}
