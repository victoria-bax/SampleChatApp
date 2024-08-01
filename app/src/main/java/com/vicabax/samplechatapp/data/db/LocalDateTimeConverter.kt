package com.vicabax.samplechatapp.data.db

import androidx.room.TypeConverter
import java.time.LocalDateTime


object LocalDateTimeConverter {
    @TypeConverter
    fun toDate(dateString: String): LocalDateTime = LocalDateTime.parse(dateString)

    @TypeConverter
    fun toDateString(date: LocalDateTime): String = date.toString()
}
