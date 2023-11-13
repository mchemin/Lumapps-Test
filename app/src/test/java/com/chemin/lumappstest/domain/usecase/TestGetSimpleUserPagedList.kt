package com.chemin.lumappstest.domain.usecase

import androidx.paging.PagingData
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.fakedata.FakeFirstName
import com.chemin.lumappstest.fakedata.FakeLastName
import com.chemin.lumappstest.fakedata.FakeSimpleDataUser
import com.chemin.lumappstest.fakedata.FakeSmallUrl
import com.chemin.lumappstest.fakedata.FakeTitle
import com.chemin.lumappstest.utils.toList
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TestGetSimpleUserPagedList {

    @Test
    fun `user display name is correctly build`() = runTest {
        this.backgroundScope.coroutineContext
        // arrange
        val usersRepository = object : GetSimpleUserPagedListImpl.UsersRepository {
            override fun getSimpleDataUerPagingData(): Flow<PagingData<SimpleDataUser>> = flowOf(
                PagingData.from(
                    data = listOf(FakeSimpleDataUser)
                )
            )
        }
        val getSimpleUserPagedListImpl = GetSimpleUserPagedListImpl(
            usersRepository = usersRepository
        )
        val expected = "$FakeTitle $FakeFirstName $FakeLastName"

        // act
        val actual = getSimpleUserPagedListImpl()
            .first() // paging data
            .toList(this.coroutineContext) // list of user
            .first() // user
            .displayName

        // assert
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `user image url is the small one`() = runTest {
        this.backgroundScope.coroutineContext
        // arrange
        val usersRepository = object : GetSimpleUserPagedListImpl.UsersRepository {
            override fun getSimpleDataUerPagingData(): Flow<PagingData<SimpleDataUser>> = flowOf(
                PagingData.from(
                    data = listOf(FakeSimpleDataUser)
                )
            )
        }
        val getSimpleUserPagedListImpl = GetSimpleUserPagedListImpl(
            usersRepository = usersRepository
        )
        val expected = FakeSmallUrl

        // act
        val actual = getSimpleUserPagedListImpl()
            .first() // paging data
            .toList(this.coroutineContext) // list of user
            .first() // user
            .imageUrl

        // assert
        assertThat(expected).isEqualTo(actual)
    }
}
