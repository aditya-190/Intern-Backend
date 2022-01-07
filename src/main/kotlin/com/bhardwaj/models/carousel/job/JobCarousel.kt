package com.bhardwaj.models.carousel.job

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class JobCarousel(
    @BsonId
    val carouselId: String = ObjectId().toString(),
    val carouselImage: String? = null,
)