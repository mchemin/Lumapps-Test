package com.chemin.lumappstest.domain.model

data class SimpleUser(
    val id: UserId,
    val displayName: String,
    val email: String,
    val imageUrl: String?,
)
