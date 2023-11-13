package com.chemin.lumappstest.domain.usecase

import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.UserDetailInfoState
import com.chemin.lumappstest.domain.model.UserId
import com.chemin.lumappstest.fakedata.FakeLargeUrl
import com.chemin.lumappstest.fakedata.FakeSimpleDataUser
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TestGetUserDetailInfo {

    @Test
    fun `user image url is the large one`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(FakeSimpleDataUser)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )
        val expected = FakeLargeUrl

        // act
        val state = getUserDetailImpl(userId = UserId("105L"))
            .toList()
            .last()
        val actual = (state as UserDetailInfoState.Success).userDetailInfo.imageUrl

        // assert
        Truth.assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `successful answer start with loading`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(FakeSimpleDataUser)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )

        // act
        val actual = getUserDetailImpl(userId = UserId("105L"))
            .toList()

        // assert
        Truth.assertThat(actual.size).isEqualTo(2)
        Truth.assertThat(actual[0]).isEqualTo(UserDetailInfoState.Loading)
        Truth.assertThat(actual[1]).isInstanceOf(UserDetailInfoState.Success::class.java)
    }

    @Test
    fun `empty information from data, provide missing user state`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(null)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )
        val expected = UserDetailInfoState.ErrorMissingUserInfo

        // act
        val actual = getUserDetailImpl(userId = UserId("105L"))
            .toList()
            .last()

        // assert
        Truth.assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `empty answer start with loading`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(null)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )

        // act
        val actual = getUserDetailImpl(userId = UserId("105L"))
            .toList()

        // assert
        Truth.assertThat(actual.size).isEqualTo(2)
        Truth.assertThat(actual[0]).isEqualTo(UserDetailInfoState.Loading)
        Truth.assertThat(actual[1]).isEqualTo(UserDetailInfoState.ErrorMissingUserInfo)
    }

    @Test
    fun `query with null user id, provide missing user id state`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(FakeSimpleDataUser)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )
        val expected = UserDetailInfoState.ErrorEmptyId

        // act
        val actual = getUserDetailImpl(userId = null)
            .toList()
            .last()

        // assert
        Truth.assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `missing user id start with loading`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(null)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )

        // act
        val actual = getUserDetailImpl(userId = null)
            .toList()

        // assert
        Truth.assertThat(actual.size).isEqualTo(2)
        Truth.assertThat(actual[0]).isEqualTo(UserDetailInfoState.Loading)
        Truth.assertThat(actual[1]).isEqualTo(UserDetailInfoState.ErrorEmptyId)
    }

    @Test
    fun `disapering user from data layer are still send`() = runTest {
        // arrange
        val usersRepository = object : GetUserDetailImpl.UsersRepository {
            override fun getUserDetail(userId: UserId): Flow<SimpleDataUser?> =
                flowOf(FakeSimpleDataUser, null)
        }
        val getUserDetailImpl = GetUserDetailImpl(
            usersRepository = usersRepository,
        )

        // act
        val actual = getUserDetailImpl(userId = UserId("105L"))
            .toList()

        // assert
        Truth.assertThat(actual.size).isEqualTo(3)
        Truth.assertThat(actual[0]).isEqualTo(UserDetailInfoState.Loading)
        Truth.assertThat(actual[1]).isInstanceOf(UserDetailInfoState.Success::class.java)
        Truth.assertThat(actual[2]).isInstanceOf(UserDetailInfoState.Success::class.java)
    }
}
