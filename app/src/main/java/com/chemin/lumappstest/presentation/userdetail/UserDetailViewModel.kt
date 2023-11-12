package com.chemin.lumappstest.presentation.userdetail

import androidx.lifecycle.ViewModel
import com.chemin.lumappstest.domain.model.UserDetailInfoState
import com.chemin.lumappstest.domain.model.UserId
import com.chemin.lumappstest.domain.usecase.GetUserDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class UserDetailViewModel(
    getUserDetail: GetUserDetail,
) : ViewModel() {

    private val userIdState = MutableStateFlow<UserId?>(null)
    val userDetailInfoState: Flow<UserDetailInfoState> =
        userIdState.flatMapLatest { userId -> getUserDetail(userId = userId) }

    fun setUserId(userId: UserId?) {
        userIdState.value = userId
    }
}
