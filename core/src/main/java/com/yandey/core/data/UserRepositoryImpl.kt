package com.yandey.core.data

import com.yandey.core.data.source.local.LocalDataSource
import com.yandey.core.data.source.remote.RemoteDataSource
import com.yandey.core.data.source.remote.network.ApiResponse
import com.yandey.core.data.source.remote.response.UserResponse
import com.yandey.core.domain.model.User
import com.yandey.core.domain.repository.IUserRepository
import com.yandey.core.utils.DataMapper.mapDomainToEntity
import com.yandey.core.utils.DataMapper.mapEntityToDomain
import com.yandey.core.utils.DataMapper.mapListEntityToDomain
import com.yandey.core.utils.DataMapper.mapListResponseToDomain
import com.yandey.core.utils.DataMapper.mapResponseToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {
    override fun getSearchUser(q: String): Flow<Resource<List<User>>> =
        object : NetworkResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                mapListResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getSearchUser(q)
        }.asFlow()

    override fun getDetailUser(username: String): Flow<Resource<User>> =
        object : NetworkResource<User, UserResponse>() {
            override fun loadFromNetwork(data: UserResponse): Flow<User> =
                mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getDetailUser(username)
        }.asFlow()

    override fun getFollowerUser(username: String): Flow<Resource<List<User>>> =
        object : NetworkResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                mapListResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowerUser(username)
        }.asFlow()

    override fun getFollowingUser(username: String): Flow<Resource<List<User>>> =
        object : NetworkResource<List<User>, List<UserResponse>>() {
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                mapListResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowingUser(username)
        }.asFlow()

    override suspend fun insertUserToDB(user: User) =
        localDataSource.insertUser(mapDomainToEntity(user))


    override fun getAllFavoriteUserFromDB(): Flow<List<User>> =
        localDataSource.getAllFavoriteUser().map { listUserEntity ->
            mapListEntityToDomain(listUserEntity)
        }

    override suspend fun deleteUserFromDB(user: User) =
        localDataSource.deleteUser(mapDomainToEntity(user))

    override fun getFavoriteStateUserFromDB(username: String): Flow<User> =
        localDataSource.getFavoriteStateUser(username).map { userEntity ->
            mapEntityToDomain(userEntity)
        }
}