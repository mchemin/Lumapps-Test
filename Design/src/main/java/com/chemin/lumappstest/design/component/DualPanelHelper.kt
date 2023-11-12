package com.chemin.lumappstest.design.component

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.ui.unit.dp

// we will allow dual panel should be available for all window expanded window
// and for medium one with landscape orientation
fun BoxWithConstraintsScope.hasSpaceForDualPanel(): Boolean =
    maxWidth > 840.dp || (maxWidth > 600.dp && maxHeight > maxWidth)
