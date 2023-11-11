package com.chemin.lumappstest.data.remote.model

import com.squareup.moshi.Json

data class UserNameDTO(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "first") val first: String,
    @field:Json(name = "last") val last: String,
)
