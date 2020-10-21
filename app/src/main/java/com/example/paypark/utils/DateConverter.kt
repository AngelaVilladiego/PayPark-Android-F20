package com.example.paypark.utils

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(dateLong: Long?) : Date? {
        //let it convert to a date
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?) : Long? {
        return date?.time
    }
}