package com.chemin.lumappstest.design.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.chemin.lumappstest.design.R

@Composable
fun FeatureNotImplemented(
    message: String? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.error)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(alignment = Alignment.Center),
        ) {
            Text(
                text = stringResource(id = R.string.feature_not_implemented),
                color = MaterialTheme.colors.onError,
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            if (!message.isNullOrBlank()) {
                Text(
                    text = message,
                    color = MaterialTheme.colors.onError,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWithoutMessage() {
    FeatureNotImplemented()
}

@Preview
@Composable
private fun PreviewWithMessage() {
    FeatureNotImplemented("Coming soon !")
}
