package com.chemin.lumappstest.di.application

import android.app.Application
import com.chemin.lumappstest.data.storage.UsersDatabase
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
class DatabaseModule {

    @SingleIn(AppScope::class)
    @Provides
    fun provideUsersDatabase(
        application: Application,
    ): UsersDatabase = UsersDatabase(
        context = application,
    )

    @Provides
    fun provideUsersDao(
        usersDatabase: UsersDatabase,
    ): UserDao = usersDatabase.userDao()
}
