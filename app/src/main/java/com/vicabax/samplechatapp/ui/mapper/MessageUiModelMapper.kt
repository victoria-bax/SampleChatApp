package com.vicabax.samplechatapp.ui.mapper

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class MessageUiModelMapper @Inject constructor(
    private val messageMapper: MessageMapper,
    private val separatorMapper: SeparatorMapper,
    private val messageTimestampMapper: MessageTimestampMapper,
) {
    fun map(message: Message, prevMessage: Message?, loggedInUser: User): List<MessageUiModel> {
        val separator = separatorMapper.map(message, prevMessage)
        val currentMapped = messageMapper.map(message, loggedInUser, separator)


        return if (prevMessage != null && lessThan1h(prevMessage.time, message.time)) {
            listOf(currentMapped)
        } else {
            val date = messageTimestampMapper.map(message)
            listOf(date, currentMapped)
        }
    }

    private fun lessThan1h(prevTime: LocalDateTime, time: LocalDateTime): Boolean =
        Duration.between(prevTime.toInstant(UTC), time.toInstant(UTC)) < Duration.of(
            1,
            ChronoUnit.HOURS
        )
}
