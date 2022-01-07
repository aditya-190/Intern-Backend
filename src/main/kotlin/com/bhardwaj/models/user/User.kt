package com.bhardwaj.models.user

import com.bhardwaj.models.category.Category
import com.bhardwaj.models.language.Language
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId
    val userId: String = ObjectId().toString(),
    val tokenId: String? = null,
    val fcmTokenId: String? = null,
    val userImage: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val verificationCode: Int? = null,
    val verificationRequestedTime: Long? = null,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long? = null,
    val categoryList: List<Category>? = null
)
