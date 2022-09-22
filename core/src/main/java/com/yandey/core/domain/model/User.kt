package com.yandey.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int?,

    val avatar_url: String?,

    val bio: String?,

    val blog: String?,

    val company: String?,

    val followers: Int?,

    val following: Int?,

    val html_url: String?,

    val location: String?,

    val login: String?,

    val name: String?,

    val public_repos: Int?,

    var isFavorite: Boolean?,
) : Parcelable