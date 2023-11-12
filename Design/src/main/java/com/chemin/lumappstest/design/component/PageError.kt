package com.chemin.lumappstest.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PageError(
    message: String,
    retryAction: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background),
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = Alignment.Center)
        ) {
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                text = "Error",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.error
            )
            Text(
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
                text = message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
            )
            Button(
                onClick = retryAction,
                modifier = Modifier
                    .align(alignment = Alignment.CenterHorizontally),
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewSimpleUserRow() {
    PageError(
        "Error while fetching the user list",
        retryAction = {},
    )
}
