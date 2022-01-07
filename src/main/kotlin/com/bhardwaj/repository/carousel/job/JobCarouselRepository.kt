package com.bhardwaj.repository.carousel.job

import com.bhardwaj.models.carousel.job.JobCarousel
import com.bhardwaj.models.carousel.job.JobCarouselResponse

interface JobCarouselRepository {
    // For Admin
    suspend fun jobCarouselById(jobCarouselId: String): JobCarousel
    suspend fun insertJobCarousel(jobCarousel: JobCarousel)
    suspend fun deleteJobCarousel(jobCarouselId: String)
    suspend fun updateJobCarousel(jobCarousel: JobCarousel): JobCarousel

    // For Client
    suspend fun getAllJobCarousel(): JobCarouselResponse
}