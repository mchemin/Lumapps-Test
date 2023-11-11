package com.chemin.lumappstest.data.storage.mapper

import com.chemin.lumappstest.data.storage.entity.UserEntity
import com.chemin.lumappstest.domain.model.SimpleDataUser
import com.chemin.lumappstest.domain.model.UserId
import com.chemin.lumappstest.domain.model.UserName

private fun UserEntity.Name.toUserName() = UserName(
    title = title,
    first = firstName,
    last = lastName,
)

private fun UserEntity.toSimpleDataUser() = SimpleDataUser(
    id = UserId(uniqueId),
    name = name.toUserName(),
    email = email,
)

fun List<UserEntity>.toListSimpleDataUser() = map { it.toSimpleDataUser() }
