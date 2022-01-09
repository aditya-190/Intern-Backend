package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import org.mindrot.jbcrypt.BCrypt

@Serializable
data class UserCredentials(
    @BsonId
    val id: String = ObjectId().toString(),
    val tokenId: String? = null,
    val fcmTokenId: String? = null,
    val name: String? = null,
    val email: String,
    val password: String,
) {
    fun hashedPassword(): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }
}
