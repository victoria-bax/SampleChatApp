package com.vicabax.samplechatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicabax.samplechatapp.HardcodedModels.ALICE
import com.vicabax.samplechatapp.HardcodedModels.BOB
import com.vicabax.samplechatapp.data.messages.MessageRepository
import com.vicabax.samplechatapp.data.model.Message
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import com.vicabax.samplechatapp.ui.mapper.MessageUiModelMapper
import com.vicabax.samplechatapp.ui.model.MessageUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MessagesListViewModel @Inject constructor(
    private val usersRepository: UserRepository,
    private val messageRepository: MessageRepository,
    private val messageMapper: MessageUiModelMapper,
) : ViewModel() {

    private val _state: MutableStateFlow<ChatScreenState> =
        MutableStateFlow(ChatScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getLoggedInUser()
                .map { loggedInUser ->
                    val friend: User = when (loggedInUser) {
                        ALICE -> BOB
                        BOB -> ALICE
                        else -> {
                            error("Unknown user: $loggedInUser")
                        }
                    }
                    loggedInUser to friend
                }.flatMapMerge { (loggedInUser, friend) ->
                    messageRepository.getMessagesForChatWith(friend)
                        .map { messages ->
                            Triple(loggedInUser, friend, messages)
                        }
                }.collect { (loggedInUser, friend, messages) ->
                    _state.value =
                        ChatScreenState.Loaded(
                            messages = messages.flatMapIndexed { index: Int, message: Message ->
                                messageMapper.map(
                                    message,
                                    messages.getOrNull(index - 1),
                                    loggedInUser
                                )
                            },
                            loggedUser = loggedInUser,
                            friend = friend,
                        )
                }
        }
    }

    fun switchUser() {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.switchLoggedInUser()
        }
    }

    fun sendMessage(text: String) {
        val state = state.value as? ChatScreenState.Loaded
        if (state != null) {
            messageRepository.addMessage(
                Message(
                    text = text,
                    from = state.loggedUser,
                    to = state.friend,
                    time = LocalDateTime.now()
                )
            )
        } else {
            //todo show some error, and make button disabled on other states
        }
    }

    sealed class ChatScreenState {
        object Loading : ChatScreenState()

        data class Loaded(
            val messages: List<MessageUiModel>,
            val loggedUser: User,
            val friend: User
        ) :
            ChatScreenState()

        data class Error(val message: String) : ChatScreenState()
    }

}
