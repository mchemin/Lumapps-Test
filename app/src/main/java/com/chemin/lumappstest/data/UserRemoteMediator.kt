package com.chemin.lumappstest.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.chemin.lumappstest.data.remote.RandomUserService
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.chemin.lumappstest.data.storage.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userDao: UserDao,
    private val randomUserService: RandomUserService,
    private val pageSize: Int,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : RemoteMediator<Int, UserEntity>() {

    // we only want to start with refresh if we don't have any user in the databse
    override suspend fun initialize(): InitializeAction = withContext(ioDispatcher) {
        if (userDao.getUserCount() == 0) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }

            LoadType.PREPEND -> {
                state
                    .pages
                    .firstOrNull() { it.data.isNotEmpty() }
                    ?.data
                    ?.firstOrNull()
                    ?.let { entity ->
                        ((entity.position / pageSize) - 1)
                            .coerceAtLeast(1)
                    }
                    ?: 1
            }

            LoadType.APPEND -> {
                state
                    .pages
                    .lastOrNull { it.data.isNotEmpty() }
                    ?.data
                    ?.lastOrNull()
                    ?.let { (it.position / pageSize) + 1 }
                    ?: 1
            }
        }
        val endOfPaginationReach: Boolean = loadType == LoadType.PREPEND && page <= 1

        return withContext(ioDispatcher) {
            try {
                val call = randomUserService
                    .userPageList(page = page, result = pageSize)
                val response = call.execute()
                val body = response.body()
                if (response?.isSuccessful == true && body != null) {
                    // insert all response
                    val userEntities = body
                        .results
                        .mapIndexed { index, userDto ->
                            UserEntity(
                                uniqueId = userDto.login.uuid,
                                name = UserEntity.Name(
                                    title = userDto.name.title,
                                    firstName = userDto.name.first,
                                    lastName = userDto.name.last,
                                ),
                                email = userDto.email,
                                position = (index + page * pageSize),
                                pictureUrl = UserEntity.PictureUrl(
                                    thumbnail = userDto.picture.thumbnail,
                                    large = userDto.picture.large,
                                )
                            )
                        }
                    if (userEntities.isNotEmpty() && loadType == LoadType.REFRESH) {
                        userDao.deleteAllUsers()
                    }
                    userDao.insert(userEntities = userEntities)
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReach)
                } else {
                    Log.e("paging", "error from empty response")
                    MediatorResult.Error(throwable = Throwable("Networking error"))
                }
            } catch (t: Throwable) {
                Log.e("paging", "Error: ${t.message}", t)
                MediatorResult.Error(throwable = t)
            }
        }
    }
}