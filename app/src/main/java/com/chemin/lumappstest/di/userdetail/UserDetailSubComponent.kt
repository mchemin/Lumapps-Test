package com.chemin.lumappstest.di.userdetail

import com.chemin.lumappstest.presentation.userdetail.UserDetailViewModel
import com.squareup.anvil.annotations.MergeSubcomponent
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Subcomponent

@MergeSubcomponent(scope = UserDetailScope::class)
@SingleIn(scope = UserDetailScope::class)
interface UserDetailSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserDetailSubComponent
    }

    fun userDetailViewModel(): UserDetailViewModel
}
