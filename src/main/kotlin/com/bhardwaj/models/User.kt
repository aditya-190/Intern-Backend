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
    val email: String? = null,
    val password: String? = null,
    val languagePreference: Language? = null,
    val pushNotificationOn: Boolean = true,
    val lastLoginTime: Long? = null,
    val categoryList: List<Category>? = null
) {
    fun hashedPassword(): String = BCrypt.hashpw(password.toString().trim(), BCrypt.gensalt())

    fun validateUser(): Boolean {
        val regexEmail = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        val regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"

        return when {
            name.isNullOrEmpty() -> false
            email.isNullOrEmpty() -> false
            !email.matches(regex = Regex(regexEmail)) -> false
            password.isNullOrEmpty() -> false
            !password.matches(regex = Regex(regexPassword)) -> false
            else -> true
        }
    }

    fun validateUserWithoutName(): Boolean {
        val regexEmail = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
        val regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$"

        return when {
            email.isNullOrEmpty() -> false
            !email.matches(regex = Regex(regexEmail)) -> false
            password.isNullOrEmpty() -> false
            !password.matches(regex = Regex(regexPassword)) -> false
            else -> true
        }
    }
}
