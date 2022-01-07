package com.bhardwaj.models.category

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Category(
    @BsonId
    val categoryId: String = ObjectId().toString(),
    val categoryName: String? = null,
    val lastUpdated: Long? = null
)
