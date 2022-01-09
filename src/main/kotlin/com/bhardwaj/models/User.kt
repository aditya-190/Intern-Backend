package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String? = null,
    val userImage: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long? = null,
    val categoryList: List<Category>? = null
)
