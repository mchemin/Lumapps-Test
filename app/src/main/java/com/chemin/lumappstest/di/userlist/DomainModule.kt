package com.chemin.lumappstest.di.userlist

import com.chemin.lumappstest.data.UsersRepository
import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedList
import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedListImpl
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(UserListScope::class)
class DomainModule {

    @Provides
    fun provideGetSimpleUserPagedList(
        usersRepository: UsersRepository,
    ): GetSimpleUserPagedList = GetSimpleUserPagedListImpl(
        usersRepository = usersRepository,
    )
}
