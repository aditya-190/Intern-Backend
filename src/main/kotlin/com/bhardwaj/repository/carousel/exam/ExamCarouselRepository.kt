package com.bhardwaj.repository.carousel.exam

import com.bhardwaj.models.ExamCarousel

interface ExamCarouselRepository {
    // For Admin
    suspend fun getExamCarouselById(examCarouselId: String): ExamCarousel?
    suspend fun insertExamCarousel(examCarousel: ExamCarousel): Boolean
    suspend fun deleteExamCarousel(examCarouselId: String): Boolean
    suspend fun updateExamCarousel(examCarousel: ExamCarousel): Boolean

    // For Client
    suspend fun getAllExamCarousel(): List<ExamCarousel>
}