package com.yandey.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yandey.core.data.source.local.entity.UserEntity
import com.yandey.core.utils.Constants.COL_ID
import com.yandey.core.utils.Constants.COL_LOGIN
import com.yandey.core.utils.Constants.USER
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM $USER ORDER BY $COL_ID ASC")
    fun getAllFavorite(): Flow<List<UserEntity>>

    @Delete
    suspend fun delete(userEntity: UserEntity)

    @Query("SELECT * FROM $USER WHERE $COL_LOGIN = :username")
    fun getFavoriteState(username: String): Flow<UserEntity>
}