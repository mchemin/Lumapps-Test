package com.chemin.lumappstest.di.userdetail

import com.chemin.lumappstest.data.UsersRepository
import com.chemin.lumappstest.domain.usecase.GetUserDetail
import com.chemin.lumappstest.domain.usecase.GetUserDetailImpl
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(UserDetailScope::class)
class DomainModule {

    @Provides
    fun provideGetSimpleUserPagedList(
        usersRepository: UsersRepository,
    ): GetUserDetail = GetUserDetailImpl(
        usersRepository = usersRepository,
    )
}
