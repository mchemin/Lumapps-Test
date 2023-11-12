package com.chemin.lumappstest.data.remote.model

import com.squareup.moshi.Json

data class UserDTO(
    @field:Json(name = "gender") val gender: String,
    @field:Json(name = "email") val email: String,
    @field:Json(name = "name") val name: UserNameDTO,
    @field:Json(name = "login") val login: LoginDTO,
    @field:Json(name = "picture") val picture: PictureDTO,
)
