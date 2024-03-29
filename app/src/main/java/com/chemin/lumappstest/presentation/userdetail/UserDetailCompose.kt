package com.chemin.lumappstest.presentation.userdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chemin.lumappstest.R
import com.chemin.lumappstest.design.component.LargeProfileImage
import com.chemin.lumappstest.design.component.PageLoader
import com.chemin.lumappstest.domain.model.UserDetailInfo
import com.chemin.lumappstest.domain.model.UserDetailInfoState
import com.chemin.lumappstest.domain.model.UserId

@Composable
fun UserDetail(
    userId: UserId?,
    userDetailViewModel: UserDetailViewModel,
) {
    userDetailViewModel.setUserId(userId = userId)
    val userDetailInfoState by userDetailViewModel.userDetailInfoState.collectAsState(initial = UserDetailInfoState.Loading)
    UserDetail(userDetailInfoState = userDetailInfoState)
}

@Composable
private fun UserDetail(
    userDetailInfoState: UserDetailInfoState,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (userDetailInfoState) {
            UserDetailInfoState.ErrorEmptyId -> UserDetailEmptyId()
            UserDetailInfoState.ErrorMissingUserInfo -> UserDetailError()
            UserDetailInfoState.Loading -> UserDetailLoading()
            is UserDetailInfoState.Success -> UserDetailView(userDetailInfo = userDetailInfoState.userDetailInfo)
        }
    }
}

@Composable
private fun UserDetailLoading() {
    PageLoader()
}

@Composable
private fun UserDetailEmptyId() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.pick_an_user),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun UserDetailError() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = stringResource(id = R.string.error_retrieving_user),
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun UserDetailView(userDetailInfo: UserDetailInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp, // TODO dimension should come from theme
                vertical = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LargeProfileImage(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            url = userDetailInfo.imageUrl,
        )
        InformationField(
            fieldName = stringResource(id = R.string.title),
            fieldValue = userDetailInfo.title,
        )
        InformationField(
            fieldName = stringResource(id = R.string.first_name),
            fieldValue = userDetailInfo.firstName,
        )
        InformationField(
            fieldName = stringResource(id = R.string.last_name),
            fieldValue = userDetailInfo.lastName,
        )
        InformationField(
            fieldName = stringResource(id = R.string.email),
            fieldValue = userDetailInfo.email,
        )
    }
}

@Composable
private fun InformationField(
    fieldName: String,
    fieldValue: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Text(
            text = fieldValue.orEmpty(),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground,
        )
        Text(
            text = fieldName,
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.primary,
        )
    }
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewLoading() {
    UserDetail(userDetailInfoState = UserDetailInfoState.Loading)
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewEmptyId() {
    UserDetail(userDetailInfoState = UserDetailInfoState.ErrorEmptyId)
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewError() {
    UserDetail(userDetailInfoState = UserDetailInfoState.ErrorMissingUserInfo)
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewData() {
    val userDetailInfo = UserDetailInfo(
        id = UserId("105L"),
        title = "Mr",
        firstName = "Matthieu",
        lastName = "Chemin",
        email = "matthieu@example.com",
        imageUrl = null,
    )
    UserDetail(userDetailInfoState = UserDetailInfoState.Success(userDetailInfo))
}
