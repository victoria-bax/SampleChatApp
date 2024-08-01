package com.vicabax.samplechatapp.di

import com.vicabax.samplechatapp.data.messages.HardcodedMessageRepository
import com.vicabax.samplechatapp.data.messages.MessageRepository
import com.vicabax.samplechatapp.data.repo.user.HardcodedUserRepository
import com.vicabax.samplechatapp.data.repo.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository =
        HardcodedUserRepository()

    @Provides
    @Singleton
    fun provideMessagesRepository(userRepository: UserRepository): MessageRepository =
        HardcodedMessageRepository(userRepository)
}
