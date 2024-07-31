package com.vicabax.samplechatapp.ui.mapper

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.ui.model.SeparatorType
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.time.temporal.ChronoUnit
import javax.inject.Inject

private const val SECONDS_UNTIL_BIG_SEPARATOR = 20L

class SeparatorMapper @Inject constructor() {
    fun map(message: Message, prevMessage: Message?): SeparatorType =
        if (prevMessage != null
            && prevMessage.from == message.from
            && lessThanThreshold(prevMessage.time, message.time)
        ) {
            SeparatorType.SMALL
        } else {
            SeparatorType.BIG
        }

    private fun lessThanThreshold(prevTime: LocalDateTime, time: LocalDateTime): Boolean =
        Duration.between(prevTime.toInstant(UTC), time.toInstant(UTC)) < Duration.of(
            SECONDS_UNTIL_BIG_SEPARATOR,
            ChronoUnit.SECONDS
        )
}
