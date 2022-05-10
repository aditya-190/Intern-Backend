package com.bhardwaj.plugins

import com.bhardwaj.routes.*
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        defaultRoute()
        categoryRoutes()
        examCarouselRoutes()
        examRoutes()
        filterRoutes()
        jobCarouselRoutes()
        jobRoutes()
        languageRoutes()
        userRoutes()
    }
}
