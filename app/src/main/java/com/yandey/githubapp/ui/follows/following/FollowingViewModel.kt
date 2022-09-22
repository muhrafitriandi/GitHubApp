package com.yandey.githubapp.ui.follows.following

import androidx.lifecycle.*
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    fun setFollowing(username: String) {
        if (this.username.value == username) return
        username.also { this.username.value = it }
    }

    val following: LiveData<Resource<List<User>>> by lazy {
        Transformations.switchMap(username) { username ->
            userUseCase.getFollowingUser(username).asLiveData()
        }
    }
}