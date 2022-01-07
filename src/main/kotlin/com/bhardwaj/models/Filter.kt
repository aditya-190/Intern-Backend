package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Filter(
    @BsonId
    val filterId: String = ObjectId().toString(),
    val filterInCategoryId: String,
    val filterName: String? = null,
    val lastUpdated: Long? = null
)
