package com.chemin.lumappstest.di.userlist

import com.chemin.lumappstest.presentation.userlist.UserListViewModel
import com.squareup.anvil.annotations.MergeSubcomponent
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.Subcomponent

@MergeSubcomponent(scope = UserListScope::class)
@SingleIn(scope = UserListScope::class)
interface UserListSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UserListSubComponent
    }

    fun userListViewModel(): UserListViewModel
}