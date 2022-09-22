package com.yandey.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListUserResponse(
    @field:SerializedName("items")
    val items: List<UserResponse>,
    @field:SerializedName("total_count")
    val total_count: Int
)

data class UserResponse(
    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("avatar_url")
    val avatar_url: String?,

    @field:SerializedName("bio")
    val bio: String?,

    @field:SerializedName("blog")
    val blog: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("followers")
    val followers: Int?,

    @field:SerializedName("following")
    val following: Int?,

    @field:SerializedName("html_url")
    val html_url: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("login")
    val login: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("public_repos")
    val public_repos: Int?
)