package com.bhardwaj.routes

import com.bhardwaj.repository.category.CategoryRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.categoryRoutes() {
    val categoryRepository: CategoryRepository by inject()
}