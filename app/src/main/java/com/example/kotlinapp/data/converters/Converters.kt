package com.example.kotlinapp.data.converters

import java.util.Calendar
import androidx.room.TypeConverter

/**
 * ---------------------------------------------------------
 * <h></h>
 * <p></p>
 * Created by Frank on 6/15/21.
 * <a href="mailto:frankyao10110@gmail.com">Contact me</a>
 * ---------------------------------------------------------
 */
class Converters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter fun datestampToCalendar(value: Long): Calendar = Calendar.getInstance().apply {
            timeInMillis = value
    }
}