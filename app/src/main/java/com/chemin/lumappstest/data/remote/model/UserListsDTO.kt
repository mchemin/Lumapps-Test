package com.chemin.lumappstest.data.remote.model

import com.squareup.moshi.Json

data class UserListsDTO(
    @field:Json(name = "results") val results: List<UserDTO>,
)