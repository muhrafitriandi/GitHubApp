package com.yandey.core.utils

import com.yandey.core.data.source.local.entity.UserEntity
import com.yandey.core.data.source.remote.response.UserResponse
import com.yandey.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapListResponseToDomain(listUserResponse: List<UserResponse>): Flow<List<User>> {
        val userList = ArrayList<User>()
        listUserResponse.map { userResponse ->
            val user = User(
                userResponse.id,
                userResponse.avatar_url,
                userResponse.bio,
                userResponse.blog,
                userResponse.company,
                userResponse.followers,
                userResponse.following,
                userResponse.html_url,
                userResponse.location,
                userResponse.login,
                userResponse.name,
                userResponse.public_repos,
                false
            )
            userList.add(user)
        }
        return flowOf(userList)
    }

    fun mapResponseToDomain(userResponse: UserResponse): Flow<User> =
        flowOf(
            User(
                userResponse.id,
                userResponse.avatar_url,
                userResponse.bio,
                userResponse.blog,
                userResponse.company,
                userResponse.followers,
                userResponse.following,
                userResponse.html_url,
                userResponse.location,
                userResponse.login,
                userResponse.name,
                userResponse.public_repos,
                false
            )
        )

    fun mapListEntityToDomain(listUserEntity: List<UserEntity>): List<User> =
        listUserEntity.map { userEntity ->
            User(
                userEntity.id,
                userEntity.avatar_url,
                userEntity.bio,
                userEntity.blog,
                userEntity.company,
                userEntity.followers,
                userEntity.following,
                userEntity.html_url,
                userEntity.location,
                userEntity.login,
                userEntity.name,
                userEntity.public_repos,
                userEntity.isFavorite
            )
        }

    fun mapEntityToDomain(userEntity: UserEntity?): User =
        User(
            userEntity?.id,
            userEntity?.avatar_url,
            userEntity?.bio,
            userEntity?.blog,
            userEntity?.company,
            userEntity?.followers,
            userEntity?.following,
            userEntity?.html_url,
            userEntity?.location,
            userEntity?.login,
            userEntity?.name,
            userEntity?.public_repos,
            userEntity?.isFavorite
        )

    fun mapDomainToEntity(user: User) =
        UserEntity(
            user.id,
            user.avatar_url,
            user.bio,
            user.blog,
            user.company,
            user.followers,
            user.following,
            user.html_url,
            user.location,
            user.login,
            user.name,
            user.public_repos,
            user.isFavorite
        )
}

