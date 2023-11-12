package com.chemin.lumappstest.design.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
private fun ProfileImage(
    modifier: Modifier,
    url: String?,
) {
    AsyncImage(
        modifier = modifier
            .clip(CircleShape),
        model = url,
        contentDescription = null,
    )
}

@Composable
fun SmallProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
) {
    ProfileImage(
        modifier = modifier.size(56.dp),
        url = url,
    )
}

@Composable
fun LargeProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
) {
    ProfileImage(
        modifier = modifier.size(128.dp),
        url = url,
    )
}
