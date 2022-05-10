package com.bhardwaj.routes

import com.bhardwaj.models.Message
import com.bhardwaj.models.User
import com.bhardwaj.repository.user.UserRepository
import com.bhardwaj.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

fun Route.defaultRoute() {
	get("/") {
		call.respond(
			status = HttpStatusCode.OK,
			message = "Hello World! Intern API is UP and ready to GO :)"
		)
	}   
}