package com.vicabax.samplechatapp.data.messages

import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessagesForChatWith(user: User): Flow<List<Message>>
}
