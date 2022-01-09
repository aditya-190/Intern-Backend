package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class User(
    @BsonId
    val userId: String,
    val userImage: String? = null,
    val name: String? = null,
    val email: String,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long? = null,
    val categoryList: List<Category>? = null
)
