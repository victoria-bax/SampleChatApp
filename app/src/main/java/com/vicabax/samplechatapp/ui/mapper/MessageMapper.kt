package com.vicabax.samplechatapp.ui.mapper

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import com.vicabax.samplechatapp.ui.model.OutgoingMessageStatus
import com.vicabax.samplechatapp.ui.model.SeparatorType
import javax.inject.Inject

class MessageMapper @Inject constructor() {
    fun map(message: Message, loggedInUser: User, separatorType: SeparatorType): MessageUiModel =
        message.run {
            if (from == loggedInUser) {
                MessageUiModel.OutgoingMessageUiModel(
                    text = text,
                    status = OutgoingMessageStatus.DELIVERED,
                    separator = separatorType,
                )
            } else {
                MessageUiModel.IncomingMessageUiModel(
                    text = text,
                    separator = separatorType,
                )
            }
        }
}
