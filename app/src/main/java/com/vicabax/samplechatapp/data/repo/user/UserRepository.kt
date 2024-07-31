package com.vicabax.samplechatapp.data.repo.user

import com.vicabax.samplechatapp.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getAllUsers(): Flow<List<User>>
    suspend fun getLoggedInUser(): Flow<User>
    suspend fun switchLoggedInUser()
}
