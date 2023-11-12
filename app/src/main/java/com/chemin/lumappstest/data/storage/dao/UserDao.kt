package com.chemin.lumappstest.data.storage.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chemin.lumappstest.data.storage.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query(
        """
        SELECT *
        FROM UserEntity
        ORDER By position
    """
    )
    fun getAllUserPaged(): PagingSource<Int, UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(userEntities: List<UserEntity>)

    @Query(
        """
        SELECT *
        FROM UserEntity
        WHERE uniqueId = :userId
    """
    )
    fun getUserById(userId: String): Flow<UserEntity?>

    @Query(
        """
            SELECT COUNT(*)
            FROM UserEntity
        """
    )
    suspend fun getUserCount(): Int

    @Query(
        """
            DELETE FROM UserEntity
        """
    )
    suspend fun deleteAllUsers()
}
