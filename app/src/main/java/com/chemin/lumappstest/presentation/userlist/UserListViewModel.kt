package com.chemin.lumappstest.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.paging.PagingData
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedList
import com.chemin.lumappstest.presentation.UserDetailScreen
import kotlinx.coroutines.flow.Flow

class UserListViewModel(
    getSimpleUserPagedList: GetSimpleUserPagedList,
    private val navController: NavController,
) : ViewModel() {

    val userList: Flow<PagingData<SimpleUser>> = getSimpleUserPagedList()

    fun onUserClick(user: SimpleUser) {
        navController.navigate(
            route = UserDetailScreen.buildForUserId(
                userId = user.id,
            ),
        )
    }
}
