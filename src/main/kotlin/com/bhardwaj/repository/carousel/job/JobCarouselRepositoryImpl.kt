package com.bhardwaj.repository.carousel.job

import com.bhardwaj.models.carousel.job.JobCarousel
import com.bhardwaj.models.carousel.job.JobCarouselResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class JobCarouselRepositoryImpl(
    private val database: CoroutineDatabase,
) : JobCarouselRepository {
    override suspend fun jobCarouselById(jobCarouselId: String): JobCarousel {
        TODO("Not yet implemented")
    }

    override suspend fun insertJobCarousel(jobCarousel: JobCarousel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteJobCarousel(jobCarouselId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateJobCarousel(jobCarousel: JobCarousel): JobCarousel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllJobCarousel(): JobCarouselResponse {
        TODO("Not yet implemented")
    }
}