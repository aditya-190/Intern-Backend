package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class User(
    @BsonId
    val userId: String = ObjectId().toString(),
    val tokenId: String? = null,
    val fcmTokenId: String? = null,
    val userImage: String? = null,
    val name: String? = null,
    val email: String,
    val password: String,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long? = null,
    val categoryList: List<Category>? = null
) {
    private val regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
    private val regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"

    fun hashedPassword(): String = BCrypt.hashpw(password.trim(), BCrypt.gensalt())

    fun validateUser() = when {
        name.isNullOrEmpty() -> false
        email.isEmpty() -> false
        !email.matches(regex = Regex(regexEmail)) -> false
        password.isEmpty() -> false
        !password.matches(regex = Regex(regexPassword)) -> false
        else -> true
    }

    fun validateUserWithoutName() = when {
        email.isEmpty() -> false
        !email.matches(regex = Regex(regexEmail)) -> false
        password.isEmpty() -> false
        !password.matches(regex = Regex(regexPassword)) -> false
        else -> true
    }
}
