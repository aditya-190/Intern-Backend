package com.bhardwaj.plugins

import com.bhardwaj.di.*
import io.ktor.application.*
import org.koin.core.logger.Level
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = Level.ERROR)
        modules(categoryModule, examModule, filterModule, jobModule, languageModule, userModule)
    }
}
