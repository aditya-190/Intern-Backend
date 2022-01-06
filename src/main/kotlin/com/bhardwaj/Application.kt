package com.bhardwaj

import com.bhardwaj.plugins.configureKoin
import com.bhardwaj.plugins.configureMonitoring
import com.bhardwaj.plugins.configureRouting
import com.bhardwaj.plugins.configureSerialization
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureMonitoring()
    configureRouting()
    configureSerialization()
}