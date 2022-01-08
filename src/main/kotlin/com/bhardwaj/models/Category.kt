package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Category(
    @BsonId
    val categoryId: String = ObjectId().toString(),
    val categoryName: String,
    val lastUpdated: Long
)
