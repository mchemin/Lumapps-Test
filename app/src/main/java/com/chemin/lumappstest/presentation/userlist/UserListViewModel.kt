package com.chemin.lumappstest.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedList
import kotlinx.coroutines.flow.Flow

class UserListViewModel(
    getSimpleUserPagedList: GetSimpleUserPagedList,
) : ViewModel() {

    val userList: Flow<PagingData<SimpleUser>> = getSimpleUserPagedList()

    fun onUserClick(user: SimpleUser) {
        // TODO
    }
}
