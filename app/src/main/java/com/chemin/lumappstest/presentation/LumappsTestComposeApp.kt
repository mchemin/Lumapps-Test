package com.chemin.lumappstest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chemin.lumappstest.di.application.AppComponent
import com.chemin.lumappstest.di.anvilViewModel
import com.chemin.lumappstest.presentation.userlist.SimpleUserList

@Composable
fun LumappsTestComposeApp(
    navController: NavHostController = rememberNavController(),
    appComponent: AppComponent,
) {
    NavHost(
        navController = navController,
        startDestination = "list",
    ) {
        composable(route = "list") { _ ->
            val subComponent = appComponent.userListComponentFactory().create()
            val userListViewModel = anvilViewModel {
                subComponent.userListViewModel()
            }
            SimpleUserList(userListViewModel)
        }
    }
}
