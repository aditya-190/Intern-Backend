package com.bhardwaj.routes

import com.bhardwaj.repository.filter.FilterRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.filterRoutes() {
    val filterRepository: FilterRepository by inject()
}