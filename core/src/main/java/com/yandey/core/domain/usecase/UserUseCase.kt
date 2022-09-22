package com.yandey.core.domain.usecase

import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getSearchUser(q: String): Flow<Resource<List<User>>>
    fun getDetailUser(username: String): Flow<Resource<User>>
    fun getFollowerUser(username: String): Flow<Resource<List<User>>>
    fun getFollowingUser(username: String): Flow<Resource<List<User>>>
    suspend fun insertUser(user: User)
    fun getAllFavoriteUser(): Flow<List<User>>
    suspend fun deleteUser(user: User)
    fun getFavoriteStateUser(username: String): Flow<User>
}