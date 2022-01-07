package com.bhardwaj.models.carousel.exam

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class ExamCarousel(
    @BsonId
    val examId: String = ObjectId().toString(),
    val examImage: String? = null,
)