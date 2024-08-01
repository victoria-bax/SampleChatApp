package com.vicabax.samplechatapp.data.repo.user

import com.vicabax.samplechatapp.HardcodedModels.ALICE
import com.vicabax.samplechatapp.HardcodedModels.BOB
import com.vicabax.samplechatapp.data.model.User
import com.vicabax.samplechatapp.data.repo.LoggedInUserPref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map


class PrefUserRepository(private val loggedInUserPref: LoggedInUserPref) :
    UserRepository {
    private val users = listOf(
        ALICE,
        BOB,
    )

    override suspend fun getAllUsers(): Flow<List<User>> = flowOf(users)

    override suspend fun getLoggedInUser(): Flow<User> =
        loggedInUserPref.get().map { name ->
            users.first { it.name == name }
        }

    override suspend fun switchLoggedInUser() {
        loggedInUserPref.set(
            when (loggedInUserPref.get().first()) {
                ALICE.name -> BOB.name
                BOB.name -> ALICE.name
                else -> error("Who are you?!")
            }
        )
    }
}
