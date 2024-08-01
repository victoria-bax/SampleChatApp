package com.vicabax.samplechatapp.data.repo.user

import com.vicabax.samplechatapp.HardcodedModels.ALICE
import com.vicabax.samplechatapp.HardcodedModels.BOB
import com.vicabax.samplechatapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf


class HardcodedUserRepository : UserRepository {
    private val users = listOf(
        ALICE,
        BOB,
    )

    private var loggedInUser: MutableStateFlow<User> = MutableStateFlow(users[0])

    override suspend fun getAllUsers(): Flow<List<User>> = flowOf(users)

    override suspend fun getLoggedInUser(): Flow<User> = loggedInUser.asStateFlow()

    override suspend fun switchLoggedInUser() {
        loggedInUser.value = when (loggedInUser.value) {
            ALICE -> BOB
            BOB -> ALICE
            else -> error("Who are you?!")
        }
    }

}
