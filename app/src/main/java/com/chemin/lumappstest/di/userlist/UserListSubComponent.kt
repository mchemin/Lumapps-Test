package com.chemin.lumappstest.di.userlist

import androidx.navigation.NavController
import com.chemin.lumappstest.presentation.userlist.UserListViewModel
import com.squareup.anvil.annotations.MergeSubcomponent
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.BindsInstance
import dagger.Subcomponent

@MergeSubcomponent(scope = UserListScope::class)
@SingleIn(scope = UserListScope::class)
interface UserListSubComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance navController: NavController,
        ): UserListSubComponent
    }

    fun userListViewModel(): UserListViewModel
}