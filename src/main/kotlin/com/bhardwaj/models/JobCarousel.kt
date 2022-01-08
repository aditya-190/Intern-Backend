package com.bhardwaj.models

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class JobCarousel(
    @BsonId
    val jobCarouselId: String = ObjectId().toString(),
    val jobCarouselImage: String,
    val placeInCarousel: String
)