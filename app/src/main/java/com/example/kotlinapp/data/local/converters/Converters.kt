package com.example.kotlinapp.data.local.converters

import java.util.Calendar
import androidx.room.TypeConverter
import timber.log.Timber
import java.lang.NumberFormatException

/**
 * ---------------------------------------------------------
 * <h></h>
 * <p></p>
 * Created by Frank on 6/15/21.
 * <a href="mailto:frankyao10110@gmail.com">Contact me</a>
 * ---------------------------------------------------------
 */
object Converters {

    @TypeConverter
    @JvmStatic
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    @JvmStatic
    fun datestampToCalendar(value: Long): Calendar = Calendar.getInstance().apply {
            timeInMillis = value
    }

    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString { "," }
    }
}