package com.bhardwaj.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val totalUsers: Int? = null,
    val userPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val users: List<User> = emptyList()
)

