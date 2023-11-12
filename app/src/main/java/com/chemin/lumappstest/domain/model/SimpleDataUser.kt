package com.chemin.lumappstest.domain.model

data class SimpleDataUser(
    val id: UserId,
    val name: UserName,
    val email: String,
    val pictureUrl: PictureUrl,
)
