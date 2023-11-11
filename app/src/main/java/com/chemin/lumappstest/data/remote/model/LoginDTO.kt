package com.chemin.lumappstest.data.remote.model

import com.squareup.moshi.Json

data class LoginDTO(
    @field:Json(name = "uuid") val uuid: String,
)
