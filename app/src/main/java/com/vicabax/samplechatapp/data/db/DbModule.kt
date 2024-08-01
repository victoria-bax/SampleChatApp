package com.vicabax.samplechatapp.data.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context: Context): MessageDb =
        Room.databaseBuilder(
            context,
            MessageDb::class.java, "message-db"
        ).build()

}
