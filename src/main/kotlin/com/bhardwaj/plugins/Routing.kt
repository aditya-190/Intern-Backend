package com.bhardwaj.plugins

import com.bhardwaj.routes.*
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World! - Intern API is UP. You are good to GO :)")
        }
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
