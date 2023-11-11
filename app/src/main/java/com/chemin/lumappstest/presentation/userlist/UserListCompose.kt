package com.chemin.lumappstest.presentation.userlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.domain.model.UserId

@Composable
fun SimpleUserList(
    userListViewModel: UserListViewModel,
) {
    val userList by userListViewModel.userList.collectAsState(initial = emptyList())
    SimpleUserList(
        users = userList,
        onUserClick = userListViewModel::onUserClick,
    )
}

@Composable
private fun SimpleUserList(
    modifier: Modifier = Modifier,
    users: List<SimpleUser>,
    onUserClick: (SimpleUser) -> Unit = {},
) {
    LazyColumn(modifier = modifier) {
        items(users) { simpleUser ->
            SimpleUserRow(
                simpleUser = simpleUser,
                onUserClick = onUserClick,
            )
            Divider(
                startIndent = 16.dp,
            )
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
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
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
    SimpleUserList(users = users)
}
