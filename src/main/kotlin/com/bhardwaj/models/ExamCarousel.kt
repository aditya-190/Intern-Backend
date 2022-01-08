package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class ExamCarousel(
    @BsonId
    val examCarouselId: String = ObjectId().toString(),
    val examCarouselImage: String,
    val placeInCarousel: String
)