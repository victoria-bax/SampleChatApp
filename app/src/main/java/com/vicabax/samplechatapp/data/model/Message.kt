package com.vicabax.samplechatapp.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    val id: String,
    val text: String,
    val time: LocalDateTime,
    @Embedded(prefix = "from_")
    val from: User,
    @Embedded(prefix = "to_")
    val to: User,
)
