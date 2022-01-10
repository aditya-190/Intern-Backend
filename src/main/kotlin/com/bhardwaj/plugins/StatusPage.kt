package com.bhardwaj.plugins

import com.bhardwaj.models.Message
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) {
            call.respond(HttpStatusCode.Unauthorized, message = Message(message = "Could not Authenticate."))
        }
    }
}