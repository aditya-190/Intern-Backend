package com.bhardwaj.models.language

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Language(
    @BsonId
    val languageId: String = ObjectId().toString(),
    val languageName: String? = null,
    val languageNameInEnglish: String? = null,
    val lastUpdated: Long? = null
)
