package com.bhardwaj.models.carousel.exam

import kotlinx.serialization.Serializable

@Serializable
data class ExamCarouselResponse(
    val totalExamCarousels: Int? = null,
    val lastUpdated: Long? = null,
    val examCarousels: List<ExamCarousel> = emptyList()
)