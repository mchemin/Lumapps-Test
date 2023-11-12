package com.chemin.lumappstest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chemin.lumappstest.design.component.FeatureNotImplemented
import com.chemin.lumappstest.design.component.hasSpaceForDualPanel
import com.chemin.lumappstest.di.anvilViewModel
import com.chemin.lumappstest.di.application.AppComponent
import com.chemin.lumappstest.domain.model.SimpleUser
import com.chemin.lumappstest.presentation.UserDetailScreen.getUserId
import com.chemin.lumappstest.presentation.userlist.SimpleUserList

@Composable
fun LumappsTestComposeApp(
    navHostController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
) {
    var userId by rememberSaveable {
        mutableStateOf<String?>(null)
    }
    BoxWithConstraints {
        val isTwoPanel = hasSpaceForDualPanel()
        val startDestination = UserListScreen.route
        NavHost(
            navController = navHostController,
            startDestination = startDestination,
        ) {
            composable(route = UserListScreen.route) {
                Row {
                    Box(
                        modifier = Modifier
                            .weight(2f)
                    ) {
                        UserListComposable(
                            appComponent = appComponent,
                            onUserClick = { simpleUser ->
                                userId = simpleUser.id.value
                                if (!isTwoPanel) {
                                    navHostController.navigate(
                                        route = UserDetailScreen.buildForUserId(
                                            userId = simpleUser.id,
                                        ),
                                    )
                                }
                            }
                        )
                    }
                    if (isTwoPanel) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                                .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)),
                        )
                        Box(
                            modifier = Modifier
                                .weight(3f)
                        ) {
                            UserDetail(userId = userId)
                        }
                    }
                }
            }
            composable(
                route = UserDetailScreen.route,
                arguments = UserDetailScreen.arguments,
            ) { backStackEntry ->
                val destinationUserId = backStackEntry
                    .getUserId()
                userId = destinationUserId
                if (isTwoPanel) {
                    navHostController.navigateUp()
                }
                UserDetail(userId = destinationUserId)
            }
        }
    }
}

@Composable
private fun UserListComposable(
    appComponent: AppComponent,
    onUserClick: (SimpleUser) -> Unit,
) {
    val subComponent = appComponent
        .userListComponentFactory()
        .create()
    val userListViewModel = anvilViewModel {
        subComponent.userListViewModel()
    }
    SimpleUserList(userListViewModel, onUserClick)
}

@Composable
private fun UserDetail(userId: String?) {
    if (userId.isNullOrBlank()) {
        FeatureNotImplemented("Missing user id - Empty view not created yet")
    } else {
        FeatureNotImplemented("User detail ($userId) is not yet implemented")
    }
}
