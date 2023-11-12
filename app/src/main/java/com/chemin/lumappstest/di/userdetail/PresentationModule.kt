package com.chemin.lumappstest.di.userdetail

import com.chemin.lumappstest.domain.usecase.GetUserDetail
import com.chemin.lumappstest.presentation.userdetail.UserDetailViewModel
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides

@Module
@ContributesTo(UserDetailScope::class)
class PresentationModule {

    @Provides
    fun provideUserDetailViewModel(
        getUserDetail: GetUserDetail,
    ): UserDetailViewModel = UserDetailViewModel(
        getUserDetail = getUserDetail,
    )
}
