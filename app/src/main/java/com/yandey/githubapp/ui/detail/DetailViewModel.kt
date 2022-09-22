package com.yandey.githubapp.ui.detail

import androidx.lifecycle.*
import com.yandey.core.data.Resource
import com.yandey.core.domain.model.User
import com.yandey.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val username: MutableLiveData<String> = MutableLiveData()

    fun setDetailUser(username: String) {
        if (this.username.value == username) return
        username.also { this.username.value = it }
    }

    val detailUser: LiveData<Resource<User>> by lazy {
        Transformations.switchMap(username) { username ->
            userUseCase.getDetailUser(username).asLiveData()
        }
    }

    fun getFavoriteStateUser(
        username: String
    ) = userUseCase.getFavoriteStateUser(username).asLiveData()

    fun insertUser(
        user: User
    ) = viewModelScope.launch {
        userUseCase.insertUser(user)
    }

    fun deleteUser(
        user: User
    ) = viewModelScope.launch {
        userUseCase.deleteUser(user)
    }
}