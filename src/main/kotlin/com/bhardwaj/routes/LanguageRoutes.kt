package com.bhardwaj.routes

import com.bhardwaj.repository.language.LanguageRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.languageRoutes() {
    val languageRepository: LanguageRepository by inject()
}