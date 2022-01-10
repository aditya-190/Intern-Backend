package com.bhardwaj

import com.bhardwaj.plugins.*
import com.bhardwaj.utils.ConnectionString.connString
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    connString = environment.config.propertyOrNull("ktor.deployment.MONGODB_URI")?.getString() ?: "mongodb://localhost"
    configureKoin()
    configureAuthentication()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureStatusPage()
}