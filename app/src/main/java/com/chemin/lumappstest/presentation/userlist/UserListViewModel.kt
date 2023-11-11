package com.chemin.lumappstest.presentation.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.usecase.GetSimpleUserList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserListViewModel(
    private val getSimpleUserList: GetSimpleUserList,
) : ViewModel() {

    private val _userList = MutableStateFlow<List<SimpleUser>>(emptyList())
    val userList: Flow<List<SimpleUser>>
        get() = _userList

    init {
        viewModelScope.launch {
            getSimpleUserList()
                .collectLatest { _userList.value = it }
        }
    }

    fun onUserClick(user: SimpleUser) {
        // TODO
    }
}
