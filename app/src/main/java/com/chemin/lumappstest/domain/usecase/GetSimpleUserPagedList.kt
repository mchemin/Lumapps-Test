package com.chemin.lumappstest.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.SimpleUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetSimpleUserPagedList {
    operator fun invoke(): Flow<PagingData<SimpleUser>>
}

class GetSimpleUserPagedListImpl(
    private val usersRepository: UsersRepository,
) : GetSimpleUserPagedList {

    interface UsersRepository {
        fun getSimpleDataUerPagingData(): Flow<PagingData<SimpleDataUser>>
    }

    override fun invoke(): Flow<PagingData<SimpleUser>> = usersRepository
        .getSimpleDataUerPagingData()
        .map { pagingData ->
            pagingData.map { userData ->
                SimpleUser(
                    id = userData.id,
                    displayName = userData.name.let { name -> "${name.title} ${name.first} ${name.last}" },
                    email = userData.email,
                    imageUrl = userData.pictureUrl.thumbnail,
                )
            }
        }
}