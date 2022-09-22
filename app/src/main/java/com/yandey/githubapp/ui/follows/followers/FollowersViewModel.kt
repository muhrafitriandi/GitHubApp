package com.yandey.githubapp.ui.follows.followers

import androidx.lifecycle.*
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    fun setFollowers(username: String) {
        if (this.username.value == username) return
        username.also { this.username.value = it }
    }

    val followers: LiveData<Resource<List<User>>> by lazy {
        Transformations.switchMap(username) { username ->
            userUseCase.getFollowerUser(username).asLiveData()
        }
    }
}