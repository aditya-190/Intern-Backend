package com.bhardwaj

import com.bhardwaj.plugins.*
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureAuthentication()
    configureMonitoring()
    configureRouting()
    configureSerialization()
}