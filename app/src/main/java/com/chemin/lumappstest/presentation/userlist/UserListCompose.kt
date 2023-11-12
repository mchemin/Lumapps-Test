package com.chemin.lumappstest.presentation.userlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.chemin.lumappstest.design.component.PageError
import com.chemin.lumappstest.design.component.PageLoader
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.model.UserId
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun SimpleUserList(
    userListViewModel: UserListViewModel,
    onUserClick: (SimpleUser) -> Unit,
) {
    val userPagingList: LazyPagingItems<SimpleUser> =
        userListViewModel.userList.collectAsLazyPagingItems()
    SimpleUserRefreshablePagingList(
        users = userPagingList,
        onUserClick = onUserClick,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SimpleUserRefreshablePagingList(
    modifier: Modifier = Modifier,
    users: LazyPagingItems<SimpleUser>,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    val refreshState = rememberPullRefreshState(
        refreshing = users.loadState.refresh is LoadState.Loading,
        onRefresh = { users.refresh() }
    )
    when {
        users.loadState.refresh is LoadState.Loading && users.itemCount == 0 -> {
            PageLoader()
        }

        users.loadState.refresh is LoadState.Error && users.itemCount == 0 -> {
            PageError(
                message = "Erreur while fetching user list",
                retryAction = { users.refresh() },
            )
        }

        else -> {
            Box(
                modifier = modifier
                    .pullRefresh(state = refreshState),
            ) {
                SimpleUserPagingList(
                    users = users,
                    onUserClick = onUserClick,
                )
                PullRefreshIndicator(
                    modifier = Modifier
                        .align(alignment = Alignment.TopCenter),
                    refreshing = users.loadState.refresh is LoadState.Loading,
                    state = refreshState,
                )
            }
        }
    }
}

@Composable
private fun SimpleUserPagingList(
    users: LazyPagingItems<SimpleUser>,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    // TODO there is an issue with the stable id here. when the loading footer become visible
    // once the loading is done, the list show different items
    // I try key = { users.peek(index)?.id ?: index } but it does not fix the issue
    LazyColumn {
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
        when (users.loadState.append) {
            // loading next page
            is LoadState.Loading -> {
                item {
                    LoadingNextPageRow()
                }
            }
            // error fetching net page
            is LoadState.Error -> {
                item {
                    LoadingErrorNextPageRow { users.retry() }
                }
            }
            // not loading
            is LoadState.NotLoading -> {
                // nothing to be done here. case only exists because when needs to be exhaustive
                // also we could display a footer to indicated the end of the list, but in our case
                // we will never reach the end of the list.
            }
        }
    }
}

@Composable
private fun SimpleUserRow(
    simpleUser: SimpleUser,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onUserClick(simpleUser) }
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(56.dp)
                .clip(shape = CircleShape),
            model = simpleUser.imageUrl,
            contentDescription = null,
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight(),
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
private fun LoadingErrorNextPageRow(
    refresh: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = MaterialTheme.colors.error)
            .clickable { refresh() },
    ) {
        Text(
            modifier = Modifier
                .align(alignment = Alignment.Center),
            text = "Error. Tap to retry.",
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
                imageUrl = null,
            )
        )
        LoadingNextPageRow()
        LoadingErrorNextPageRow(refresh = {})
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
            imageUrl = null,
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
            imageUrl = null,
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
            imageUrl = null,
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
            imageUrl = null,
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
            imageUrl = null,
        ),
        SimpleUser(
            id = UserId("105L"),
            displayName = "Matthieu Chemin",
            email = "matt@example.com",
            imageUrl = null,
        ),
    )
    val flow = MutableStateFlow(PagingData.from(users))
    val lazyPagingItems = flow.collectAsLazyPagingItems()
    SimpleUserRefreshablePagingList(users = lazyPagingItems)
}
