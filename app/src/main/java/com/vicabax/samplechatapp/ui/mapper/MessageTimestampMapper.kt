package com.vicabax.samplechatapp.ui.mapper

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class MessageTimestampMapper @Inject constructor() {
    fun map(message: Message) : MessageUiModel.DateTimeUiModel =
        MessageUiModel.DateTimeUiModel(
            day = message.time.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH),
            time = message.time.format(formatter)
        )

    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
