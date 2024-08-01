package com.vicabax.samplechatapp.data.repo.messages

import com.vicabax.samplechatapp.HardcodedModels
import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HardcodedMessageRepository @Inject constructor(
    private val userRepository: UserRepository
) :
    MessageRepository {

    private val messages: MutableStateFlow<List<Message>> =
        MutableStateFlow(HardcodedModels.messages)

    override suspend fun getMessagesForChatWith(user: User): Flow<List<Message>> {
        val loggedInUser = userRepository.getLoggedInUser().first()
        return messages.map { list ->
            list.filter { message ->
                isChatBetween(message, loggedInUser, user)
            }.sortedBy { it.time }
        }
    }

    private fun isChatBetween(message: Message, first: User, second: User): Boolean =
        when (message.from to message.to) {
            first to second, second to first -> true
            else -> false
        }

    override suspend fun addMessage(message: Message) {
        messages.value = messages.value + message
    }
}
