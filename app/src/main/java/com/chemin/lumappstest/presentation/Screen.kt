package com.chemin.lumappstest.presentation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.chemin.lumappstest.domain.model.UserId

object UserListScreen {
    const val route: String = "user_list"
}

object UserDetailScreen {
    private const val userIdParamName = "user_id"
    const val route: String = "user/{$userIdParamName}"
    val arguments = listOf(
        navArgument(userIdParamName) { type = NavType.StringType },
    )

    fun buildForUserId(userId: UserId) = "user/${userId.value}"
    fun NavBackStackEntry.getUserId(): String? = this
        .arguments
        ?.getString(userIdParamName)
}
