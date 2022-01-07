package com.bhardwaj.routes

import com.bhardwaj.repository.LanguageRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.languageRoutes() {
    val languageRepository: LanguageRepository by inject()
}