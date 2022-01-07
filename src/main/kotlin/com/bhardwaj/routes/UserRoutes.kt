package com.bhardwaj.routes

import com.bhardwaj.repository.UserRepository
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userRepository: UserRepository by inject()
}