package com.chemin.lumappstest.di.application

import android.app.Application
import com.chemin.lumappstest.di.userlist.UserListSubComponent
import com.chemin.lumappstest.presentation.userlist.UserListViewModel
import com.squareup.anvil.annotations.MergeComponent
import com.squareup.anvil.annotations.optional.SingleIn
import dagger.BindsInstance
import dagger.Component

@MergeComponent(scope = AppScope::class)
@SingleIn(AppScope::class)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance @BaseUrl baseUrl: String,
        ): AppComponent
    }
    fun userListComponentFactory(): UserListSubComponent.Factory
}