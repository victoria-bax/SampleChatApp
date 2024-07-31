package com.vicabax.samplechatapp.ui.mapper

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import com.vicabax.samplechatapp.ui.model.OutgoingMessageStatus
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale
import javax.inject.Inject

class MessageMapper @Inject constructor() {
    fun map(message: Message, prevMessage: Message?, loggedInUser: User): List<MessageUiModel> {
        val currentMapped = message.run {
            if (from == loggedInUser) {
                MessageUiModel.OutgoingMessageUiModel(
                    text = text,
                    status = OutgoingMessageStatus.DELIVERED
                )
            } else {
                MessageUiModel.IncomingMessageUiModel(
                    text = text,
                )
            }
        }
        return if (prevMessage != null && lessThan1h(prevMessage.time, message.time)) {
            listOf(currentMapped)
        } else {
            val date = MessageUiModel.DateTimeUiModel(
                day = message.time.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
                time = message.time.format(formatter)
            )
            listOf(date, currentMapped)
        }
    }

    private fun lessThan1h(prevTime: LocalDateTime, time: LocalDateTime): Boolean =
        Duration.between(prevTime.toInstant(UTC), time.toInstant(UTC)) < Duration.of(
            1,
            ChronoUnit.HOURS
        )


    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
