package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Exam(
    @BsonId
    val postId: String = ObjectId().toString(),
    val examLogo: String? = null,
    val examName: String,
    val examOrganiser: String,
    val numberOfRegistered: Int = 0,
    val lastDateToRegister: String,
    val registerPage: String,
    val examImportantDetails: String? = null,
    val examRules: String? = null,
    val examEligibility: String? = null,
    val lastUpdated: Long
)