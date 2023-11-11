package com.chemin.lumappstest.domain.usecase

import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.SimpleUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetSimpleUserList {
    operator fun invoke(): Flow<List<SimpleUser>>
}

class GetSimpleUserListImpl(
    private val usersRepository: UsersRepository,
) : GetSimpleUserList {

    interface UsersRepository {
        fun getUserList(): Flow<List<SimpleDataUser>>
    }

    override fun invoke(): Flow<List<SimpleUser>> = usersRepository
        .getUserList()
        .map { userDataList ->
            userDataList.map { userData ->
                SimpleUser(
                    id = userData.id,
                    displayName = userData.name.let { name -> "${name.title} ${name.first} ${name.last}" },
                    email = userData.email,
                )
            }
        }
}