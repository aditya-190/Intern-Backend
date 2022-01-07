package com.bhardwaj.routes

import com.bhardwaj.repository.carousel.exam.ExamCarouselRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.examCarouselRoutes() {
    val examCarouselRepository: ExamCarouselRepository by inject()
}