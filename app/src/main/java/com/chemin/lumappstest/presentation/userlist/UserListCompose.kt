package com.chemin.lumappstest.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.chemin.lumappstest.design.component.FeatureNotImplemented
import com.chemin.lumappstest.design.component.PageLoader
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.model.UserId
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SimpleUserList(
    userListViewModel: UserListViewModel,
) {
    val userPagingList: LazyPagingItems<SimpleUser> =
        userListViewModel.userList.collectAsLazyPagingItems()
    SimpleUserPagingList(
        users = userPagingList,
        onUserClick = userListViewModel::onUserClick,
    )
}

@Composable
private fun SimpleUserPagingList(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<SimpleUser>,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    // TODO there is an issue with the stable id here. when the loading footer become visible
    // once the loading is done, the list show different items
    // I try key = { users.peek(index)?.id ?: index } but it does not fix the issue
    LazyColumn(modifier = modifier) {
        // user list content
        items(
            count = users.itemCount,
        ) { index ->
            users[index]?.let { simpleUser ->
                SimpleUserRow(
                    simpleUser = simpleUser,
                    onUserClick = onUserClick,
                )
            } // TODO placeholder for null users
            Divider()
        }
        // loading state
        users.apply {
            when {
                // refreshing from start
                loadState.refresh is LoadState.Loading -> {
                    item {
                        PageLoader()
                    }
                }
                // error while first refresh
                loadState.refresh is LoadState.Error -> {
                    item {
                        FeatureNotImplemented("Error case not handled")
                    }
                }
                // loading next page
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingNextPageRow()
                    }
                }
                // error fetching net page
                loadState.append is LoadState.Error -> {
                    item {
                        LoadingErrorNextPageRow()
                    }
                }
            }
        }
    }
}

@Composable
private fun SimpleUserRow(
    simpleUser: SimpleUser,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .clickable { onUserClick(simpleUser) }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = simpleUser.displayName,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
        )
        Text(
            text = simpleUser.email,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
        )
    }
}

@Composable
private fun LoadingNextPageRow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
private fun LoadingErrorNextPageRow() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = MaterialTheme.colors.error),
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            text = "Error while fetching data",
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onError,
        )
    }
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewSimpleUserRow() {
    Column {
        SimpleUserRow(
            simpleUser = SimpleUser(
                id = UserId("105L"),
                displayName = "Matthieu Chemin",
                email = "matt@example.com",
            )
        )
        LoadingNextPageRow()
        LoadingErrorNextPageRow()
    }
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewSimpleUserList() {
    val users = listOf(
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
        ),
    )
    val flow = MutableStateFlow(PagingData.from(users))
    val lazyPagingItems = flow.collectAsLazyPagingItems()
    SimpleUserPagingList(users = lazyPagingItems)
}
