package com.chemin.lumappstest.di.userlist

import com.chemin.lumappstest.data.UsersRepository
import com.chemin.lumappstest.domain.usecase.GetSimpleUserList
import com.chemin.lumappstest.domain.usecase.GetSimpleUserListImpl
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(UserListScope::class)
class DomainModule {

    @Provides
    fun provideGetSimpleUserList(
        usersRepository: UsersRepository,
    ): GetSimpleUserList = GetSimpleUserListImpl(
        usersRepository = usersRepository,
    )
}
