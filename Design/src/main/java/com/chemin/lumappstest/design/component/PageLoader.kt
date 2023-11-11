package com.chemin.lumappstest.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PageLoader() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size = 96.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Preview(name = "Nexus 7", device = Devices.NEXUS_7, showSystemUi = true)
@Composable
private fun PreviewSimpleUserRow() {
    PageLoader()
}
