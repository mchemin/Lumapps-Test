package com.chemin.lumappstest.data.storage.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val uniqueId: String,
    @Embedded(prefix = "name_") val name: Name,
    val email: String,
    val position: Int,
) {
    data class Name(
        val title: String,
        val firstName: String,
        val lastName: String,
    )
}
