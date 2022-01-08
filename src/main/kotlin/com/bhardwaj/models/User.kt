package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId
    val userId: String = ObjectId().toString(),
    val tokenId: String,
    val fcmTokenId: String? = null,
    val userImage: String? = null,
    val name: String,
    val email: String,
    val password: String,
    val verificationCode: Int? = null,
    val verificationRequestedTime: Long? = null,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long,
    val categoryList: List<Category>? = null
)
