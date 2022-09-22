package com.yandey.githubapp.ui.home

import androidx.lifecycle.*
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    fun searchUser(q: String) {
        if (username.value == q) return
        q.also { username.value = it }
    }

    val user: LiveData<Resource<List<User>>> by lazy {
        Transformations.switchMap(username) { q ->
            userUseCase.getSearchUser(q).asLiveData()
        }
    }
}