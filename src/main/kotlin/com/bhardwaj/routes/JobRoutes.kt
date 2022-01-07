package com.bhardwaj.routes

import com.bhardwaj.repository.job.JobRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.jobRoutes() {
    val jobRepository: JobRepository by inject()
}