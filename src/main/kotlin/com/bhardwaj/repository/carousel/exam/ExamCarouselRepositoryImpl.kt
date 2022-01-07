package com.bhardwaj.repository.carousel.exam

import com.bhardwaj.models.carousel.exam.ExamCarousel
import com.bhardwaj.models.carousel.exam.ExamCarouselResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class ExamCarouselRepositoryImpl(
    private val database: CoroutineDatabase,
) : ExamCarouselRepository {
    override suspend fun examCarouselById(examCarouselId: String): ExamCarousel {
        TODO("Not yet implemented")
    }

    override suspend fun insertExamCarousel(examCarousel: ExamCarousel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteExamCarousel(examCarouselId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExamCarousel(examCarousel: ExamCarousel): ExamCarousel {
        TODO("Not yet implemented")
    }

    override suspend fun getAllExamCarousel(): ExamCarouselResponse {
        TODO("Not yet implemented")
    }
}