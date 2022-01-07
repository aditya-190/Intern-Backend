package com.bhardwaj.models.carousel.job

import kotlinx.serialization.Serializable

@Serializable
data class JobCarouselResponse(
    val totalJobCarousels: Int? = null,
    val lastUpdated: Long? = null,
    val jobsCarousels: List<JobCarousel> = emptyList()
)