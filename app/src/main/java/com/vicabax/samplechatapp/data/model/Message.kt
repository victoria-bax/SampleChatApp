package com.vicabax.samplechatapp.data.model

import java.time.LocalDateTime

data class Message(
    val text: String,
    val time: LocalDateTime,
    val from: User,
    val to: User,
)
