package com.bhardwaj.repository.carousel.job

import com.bhardwaj.models.JobCarousel

interface JobCarouselRepository {
    // For Admin
    suspend fun getJobCarouselById(jobCarouselId: String): JobCarousel?
    suspend fun insertJobCarousel(jobCarousel: JobCarousel): Boolean
    suspend fun deleteJobCarousel(jobCarouselId: String): Boolean
    suspend fun updateJobCarousel(jobCarousel: JobCarousel): Boolean

    // For Client
    suspend fun getAllJobCarousel(): List<JobCarousel>
}