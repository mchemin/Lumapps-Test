package com.chemin.lumappstest.di.userlist

import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedList
import com.chemin.lumappstest.presentation.userlist.UserListViewModel
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(UserListScope::class)
class PresentationModule {

    @Provides
    fun provideUserListViewModel(
        getSimpleUserPagedList: GetSimpleUserPagedList,
    ): UserListViewModel = UserListViewModel(
        getSimpleUserPagedList = getSimpleUserPagedList,
    )
}
