package com.vicabax.samplechatapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vicabax.samplechatapp.data.model.Message
import javax.inject.Singleton

@Singleton
@TypeConverters(LocalDateTimeConverter::class)
@Database(entities = [Message::class], version = 1)
abstract class MessageDb : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}
