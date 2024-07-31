package com.vicabax.samplechatapp.data.messages

import com.vicabax.samplechatapp.HardcodedModels
import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HardcodedMessageRepository : MessageRepository {
    override suspend fun getMessagesForChatWith(user: User): Flow<List<Message>> =
        flowOf(HardcodedModels.messages.filter {
            it.from == user || it.to == user
        }.sortedBy { it.time })
}
