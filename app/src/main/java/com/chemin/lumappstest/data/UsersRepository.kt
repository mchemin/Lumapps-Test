package com.chemin.lumappstest.data

import com.chemin.lumappstest.data.remote.RandomUserService
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.chemin.lumappstest.data.storage.entity.UserEntity
import com.chemin.lumappstest.data.storage.mapper.toListSimpleDataUser
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.usecase.GetSimpleUserListImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UsersRepository(
    private val userDao: UserDao,
    private val randomUserService: RandomUserService,
) : GetSimpleUserListImpl.UsersRepository {

    override fun getUserList(): Flow<List<SimpleDataUser>> =
        userDao
            .getAll()
            .onStart {
                // TODO clear ?
                fetchUserPage(page = 1)
            }
            .map { list -> list.toListSimpleDataUser() }

    private suspend fun fetchUserPage(page: Int) =
        withContext(Dispatchers.IO) { // TODO dispatchers injection
            val call = randomUserService
                .userPageList(page = page)
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
                            position = index + page * 10L, // TODO this is a bad computation
                        )
                    }
                userDao.insert(userEntities = userEntities)
            } else {
                // TODO error loading
            }
        }
}
