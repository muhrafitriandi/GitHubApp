package com.yandey.core.domain.repository

import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getSearchUser(q: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun getFollowerUser(username: String): Flow<Resource<List<User>>>
    fun getFollowingUser(username: String): Flow<Resource<List<User>>>
    suspend fun insertUserToDB(user: User)
    fun getAllFavoriteUserFromDB(): Flow<List<User>>
    suspend fun deleteUserFromDB(user: User)
    fun getFavoriteStateUserFromDB(username: String): Flow<User>
}