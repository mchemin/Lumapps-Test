package com.chemin.lumappstest.domain.usecase

import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.UserDetailInfo
import com.chemin.lumappstest.domain.model.UserDetailInfoState
import com.chemin.lumappstest.domain.model.UserId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

interface GetUserDetail {
    operator fun invoke(userId: UserId?): Flow<UserDetailInfoState>
}

class GetUserDetailImpl(
    private val usersRepository: UsersRepository,
) : GetUserDetail {

    interface UsersRepository {
        fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> // TODO put richer data here
    }

    override fun invoke(userId: UserId?): Flow<UserDetailInfoState> {
        var lastUserDetailInfo: UserDetailInfo? = null
        val flow = userId?.let { id ->
            usersRepository
                .getUserDetail(userId = id)
                .map { userData ->
                    if (userData != null) {
                        val detail = UserDetailInfo(
                            id = userData.id,
                            title = userData.name.title,
                            firstName = userData.name.first,
                            lastName = userData.name.last,
                            email = userData.email
                        )
                        UserDetailInfoState.Success(userDetailInfo = detail)
                    } else {
                        UserDetailInfoState.ErrorMissingUserInfo
                    }
                }
        } ?: flowOf(UserDetailInfoState.ErrorEmptyId)
        return flow
            .onStart { emit(UserDetailInfoState.Loading) }
            // we keep track of the last user info we get
            .onEach { userDetailInfoState ->
                if (userDetailInfoState is UserDetailInfoState.Success) {
                    lastUserDetailInfo = userDetailInfoState.userDetailInfo
                }
            }
            // if the user become missing, we replace it by its last representation if existing
            .map { userDetailInfoState ->
                lastUserDetailInfo?.let { userDetailInfo ->
                    if (userDetailInfoState is UserDetailInfoState.ErrorMissingUserInfo) {
                        UserDetailInfoState.Success(userDetailInfo)
                    } else {
                        userDetailInfoState
                    }
                } ?: userDetailInfoState
            }

    }
}
