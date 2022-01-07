package com.bhardwaj.routes

import com.bhardwaj.repository.exam.ExamRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.examRoutes() {
    val examRepository: ExamRepository by inject()
}