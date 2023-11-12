package com.chemin.lumappstest.domain.model

sealed class UserDetailInfoState {
    data object Loading : UserDetailInfoState()
    data object ErrorEmptyId : UserDetailInfoState()
    data object ErrorMissingUserInfo : UserDetailInfoState()
    data class Success(val userDetailInfo: UserDetailInfo) : UserDetailInfoState()
}
