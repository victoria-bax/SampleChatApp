package com.vicabax.samplechatapp.ui.model

sealed class MessageUiModel(val type: Int) {
    data class IncomingMessageUiModel(val text: String) : MessageUiModel(INCOMING_TYPE)

    data class OutgoingMessageUiModel(val text: String, val status: OutgoingMessageStatus) :
        MessageUiModel(
            OUTGOING_TYPE
        )

    data class DateTimeUiModel(val day: String, val time: String) : MessageUiModel(TIMESTAMP_TYPE)

    companion object {
        const val INCOMING_TYPE = 1
        const val OUTGOING_TYPE = 2
        const val TIMESTAMP_TYPE = 3
    }
}
