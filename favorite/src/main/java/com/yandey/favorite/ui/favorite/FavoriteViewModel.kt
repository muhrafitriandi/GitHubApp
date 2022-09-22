package com.yandey.favorite.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yandey.core.domain.usecase.UserUseCase

class FavoriteViewModel(
    userUseCase: UserUseCase
) : ViewModel() {
    val favoriteUsers = userUseCase.getAllFavoriteUser().asLiveData()
}