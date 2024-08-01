package com.vicabax.samplechatapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vicabax.samplechatapp.data.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(message: Message)

    @Query(
        "SELECT * FROM messages " +
                "WHERE from_name == :name1 AND to_name == :name2 " +
                "OR to_name == :name1 AND from_name == :name2 " +
                "ORDER BY time"
    )
    fun getMessages(name1: String, name2: String): Flow<List<Message>>

    @Query("DELETE FROM messages")
    suspend fun clearAll()
}
