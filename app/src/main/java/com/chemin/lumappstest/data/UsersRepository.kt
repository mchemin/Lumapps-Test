package com.chemin.lumappstest.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chemin.lumappstest.data.remote.RandomUserService
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.chemin.lumappstest.data.storage.mapper.toSimpleDataUser
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.usecase.GetSimpleUserPagedListImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepository(
    private val userDao: UserDao,
    private val randomUserService: RandomUserService,
    private val pageSize: Int,
) : GetSimpleUserPagedListImpl.UsersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getSimpleDataUerPagingData(): Flow<PagingData<SimpleDataUser>> {
        val remoteMediator = UserRemoteMediator(
            pageSize = pageSize,
            userDao = userDao,
            randomUserService = randomUserService,
        )
        val config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false,
        )
        val pagingSourceFactory = { userDao.getAllUserPaged() }
        val pager = Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory,
        )
        return pager
            .flow
            .map { pagingData ->
                pagingData.map { it.toSimpleDataUser() }
            }
    }
}
