package com.bhardwaj.routes

import com.bhardwaj.repository.carousel.job.JobCarouselRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.jobCarouselRoutes() {
    val jobCarouselRepository: JobCarouselRepository by inject()
}