package com.yandey.core.data.source.remote

import com.yandey.core.data.source.remote.network.ApiResponse
import com.yandey.core.data.source.remote.network.ApiService
import com.yandey.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getSearchUser(q: String): Flow<ApiResponse<List<UserResponse>>> = flow {
        try {
            val response = apiService.search(q)
            val user = response.items
            if (user.isNotEmpty()) {
                emit(ApiResponse.Success(user))
            } else {
                emit(ApiResponse.Error(null))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(exception.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getDetailUser(username: String): Flow<ApiResponse<UserResponse>> = flow {
        try {
            val response = apiService.detail(username)
            if (username.isNotEmpty()) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(null))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(exception.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getFollowerUser(username: String): Flow<ApiResponse<List<UserResponse>>> = flow {
        try {
            val response = apiService.follower(username)
            if (response.isNotEmpty()) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(null))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(exception.toString()))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getFollowingUser(username: String): Flow<ApiResponse<List<UserResponse>>> = flow {
        try {
            val response = apiService.following(username)
            if (response.isNotEmpty()) {
                emit(ApiResponse.Success(response))
            } else {
                emit(ApiResponse.Error(null))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(exception.toString()))
        }
    }.flowOn(Dispatchers.IO)
}