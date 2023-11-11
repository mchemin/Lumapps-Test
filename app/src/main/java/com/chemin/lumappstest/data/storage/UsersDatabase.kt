package com.chemin.lumappstest.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chemin.lumappstest.data.storage.dao.UserDao
import com.chemin.lumappstest.data.storage.entity.UserEntity

private const val VERSION = 1
private const val NAME = "users"

@Database(
    entities = [
        UserEntity::class,
    ],
    version = VERSION
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        operator fun invoke(
            context: Context,
        ) = Room.databaseBuilder(
            context = context,
            klass = UsersDatabase::class.java,
            name = NAME,
        ).build()
    }
}