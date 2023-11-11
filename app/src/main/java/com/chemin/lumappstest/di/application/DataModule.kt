package com.chemin.lumappstest.di.application

import com.chemin.lumappstest.data.UsersRepository
import com.chemin.lumappstest.data.remote.RandomUserService
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.squareup.anvil.annotations.ContributesTo
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(AppScope::class)
class DataModule {

    @Provides
    @SingleIn(AppScope::class)
    fun provideUsersRepository(
        userDao: UserDao,
        randomUserService: RandomUserService,
    ) = UsersRepository(
        userDao = userDao,
        randomUserService = randomUserService,
    )
}
