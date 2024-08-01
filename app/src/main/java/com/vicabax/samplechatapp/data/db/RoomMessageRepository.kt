package com.vicabax.samplechatapp.data.db

import com.vicabax.samplechatapp.data.messages.MessageRepository
import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RoomMessageRepository @Inject constructor(
    db: MessageDb,
    private val userRepository: UserRepository,
) : MessageRepository {

    private val dao = db.messageDao()
    override suspend fun getMessagesForChatWith(user: User): Flow<List<Message>> {
        val loggedInUser = userRepository.getLoggedInUser().first()
        return dao.getMessages(loggedInUser.name, user.name)
    }

    override suspend fun addMessage(message: Message) {
        dao.insertOne(message)
    }
}
