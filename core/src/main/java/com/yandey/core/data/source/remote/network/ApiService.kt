package com.yandey.core.data.source.remote.network

import com.yandey.core.data.source.remote.response.ListUserResponse
import com.yandey.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun search(
        @Query("q") q: String
    ): ListUserResponse

    @GET("users/{username}")
    suspend fun detail(
        @Path("username") username: String
    ): UserResponse

    @GET("users/{username}/followers")
    suspend fun follower(
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun following(
        @Path("username") username: String
    ): List<UserResponse>
}