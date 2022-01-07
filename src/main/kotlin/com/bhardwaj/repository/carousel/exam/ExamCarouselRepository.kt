package com.bhardwaj.repository.carousel.exam

import com.bhardwaj.models.carousel.exam.ExamCarousel
import com.bhardwaj.models.carousel.exam.ExamCarouselResponse

interface ExamCarouselRepository {
    // For Admin
    suspend fun examCarouselById(examCarouselId: String): ExamCarousel
    suspend fun insertExamCarousel(examCarousel: ExamCarousel)
    suspend fun deleteExamCarousel(examCarouselId: String)
    suspend fun updateExamCarousel(examCarousel: ExamCarousel): ExamCarousel

    // For Client
    suspend fun getAllExamCarousel(): ExamCarouselResponse
}