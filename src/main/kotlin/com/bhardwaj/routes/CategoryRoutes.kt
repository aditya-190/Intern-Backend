package com.bhardwaj.routes

import com.bhardwaj.repository.CategoryRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.categoryRoutes() {
    val categoryRepository: CategoryRepository by inject()
}