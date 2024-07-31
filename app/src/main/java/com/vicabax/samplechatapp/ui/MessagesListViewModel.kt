package com.vicabax.samplechatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicabax.samplechatapp.HardcodedModels.ALICE
import com.vicabax.samplechatapp.HardcodedModels.BOB
import com.vicabax.samplechatapp.data.messages.MessageRepository
import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import com.vicabax.samplechatapp.ui.model.OutgoingMessageStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MessagesListViewModel @Inject constructor(
    private val usersRepository: UserRepository,
    private val messageRepository: MessageRepository
) : ViewModel() {

    private val _messages: MutableStateFlow<List<MessageUiModel>> =
        MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getLoggedInUser()
                .collect { loggedInUser ->
                    val friend: User? = when (loggedInUser) {
                        ALICE -> BOB
                        BOB -> ALICE
                        else -> {
                            Timber.e("Unknown user: $loggedInUser")
                            null
                        }
                    }
                    friend?.let { user ->
                        messageRepository.getMessagesForChatWith(user)
                            .collect{ list ->
                                _messages.value = list.map { message: Message ->
                                    // todo make mapper
                                    if (message.from == loggedInUser) {
                                        //todo work with statuses
                                        MessageUiModel.OutgoingMessageUiModel(
                                            message.text,
                                            OutgoingMessageStatus.SENT
                                        )
                                    } else {
                                        MessageUiModel.IncomingMessageUiModel(message.text)
                                    }
                                }
                            }
                        // todo insert timestamps
                        // todo add separators
                    }

                }

        }
    }

}
