package com.chemin.lumappstest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.chemin.lumappstest.design.component.FeatureNotImplemented
import com.chemin.lumappstest.di.application.AppComponent
import com.chemin.lumappstest.di.anvilViewModel
import com.chemin.lumappstest.presentation.UserDetailScreen.getUserId
import com.chemin.lumappstest.presentation.userlist.SimpleUserList

@Composable
fun LumappsTestComposeApp(
    navController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
) {
    NavHost(
        navController = navController,
        startDestination = UserListScreen.route,
    ) {
        composable(route = UserListScreen.route) { _ ->
            val subComponent = appComponent
                .userListComponentFactory()
                .create(
                    navController = navController,
                )
            val userListViewModel = anvilViewModel {
                subComponent.userListViewModel()
            }
            SimpleUserList(userListViewModel)
        }
        composable(
            route = UserDetailScreen.route,
            arguments = UserDetailScreen.arguments,
        ) { backStackEntry ->
            backStackEntry
                .getUserId()
                ?.let { userId ->
                    FeatureNotImplemented("User detail ($userId) is not yet implemented")
                }
                ?: FeatureNotImplemented("Missing user id") // TODO create an error string for this
        }
    }
}
