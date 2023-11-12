package com.chemin.lumappstest.data.remote.model

import com.squareup.moshi.Json

data class PictureDTO(
    @field:Json(name = "thumbnail") val thumbnail: String,
    @field:Json(name = "large") val large: String,
)
