package com.yandey.core.domain.usecase

import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(
    private val userRepository: IUserRepository
) : UserUseCase {
    override fun getSearchUser(q: String): Flow<Resource<List<User>>> =
        userRepository.getSearchUser(q)

    override fun getDetailUser(username: String): Flow<Resource<User>> =
        userRepository.getDetailUser(username)

    override fun getFollowerUser(username: String): Flow<Resource<List<User>>> =
        userRepository.getFollowerUser(username)

    override fun getFollowingUser(username: String): Flow<Resource<List<User>>> =
        userRepository.getFollowingUser(username)

    override suspend fun insertUser(user: User) =
        userRepository.insertUserToDB(user)

    override fun getAllFavoriteUser(): Flow<List<User>> =
        userRepository.getAllFavoriteUserFromDB()

    override suspend fun deleteUser(user: User) =
        userRepository.deleteUserFromDB(user)

    override fun getFavoriteStateUser(username: String): Flow<User> =
        userRepository.getFavoriteStateUserFromDB(username)
}