package com.vicabax.samplechatapp.di

import android.content.Context
import com.vicabax.samplechatapp.data.db.MessageDb
import com.vicabax.samplechatapp.data.repo.LoggedInUserPref
import com.vicabax.samplechatapp.data.repo.messages.MessageRepository
import com.vicabax.samplechatapp.data.repo.messages.RoomMessageRepository
import com.vicabax.samplechatapp.data.repo.user.PrefUserRepository
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideLoggedUserPref(@ApplicationContext context: Context): LoggedInUserPref =
        LoggedInUserPref.with(context)

    @Provides
    @Singleton
    fun provideUserRepository(loggedInUserPref: LoggedInUserPref): UserRepository =
        PrefUserRepository(loggedInUserPref)

    @Provides
    @Singleton
    fun provideMessagesRepository(
        messageDb: MessageDb,
        userRepository: UserRepository
    ): MessageRepository =
        RoomMessageRepository(messageDb, userRepository)
}
