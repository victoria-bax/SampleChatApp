package com.vicabax.samplechatapp.ui.model

sealed class MessageUiModel(val type: Int, val separatorType: SeparatorType) {
    data class IncomingMessageUiModel(
        val text: String,
        val separator: SeparatorType,
    ) : MessageUiModel(INCOMING_TYPE, separator)

    data class OutgoingMessageUiModel(
        val text: String,
        val status: OutgoingMessageStatus,
        val separator: SeparatorType,
    ) : MessageUiModel(OUTGOING_TYPE, separator)

    data class DateTimeUiModel(
        val day: String,
        val time: String,
    ) : MessageUiModel(TIMESTAMP_TYPE, SeparatorType.BIG)

    companion object {
        const val INCOMING_TYPE = 1
        const val OUTGOING_TYPE = 2
        const val TIMESTAMP_TYPE = 3
    }
}
