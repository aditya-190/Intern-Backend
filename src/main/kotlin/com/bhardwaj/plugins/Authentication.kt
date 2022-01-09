package com.bhardwaj.plugins

import com.bhardwaj.utils.TokenManager
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.config.*

fun Application.configureAuthentication() {
    val config = HoconApplicationConfig(ConfigFactory.load())
    val tokenManager = TokenManager(config = config)

    authentication {
        jwt("auth-jwt") {
            verifier(tokenManager.verifyToken())
            realm = config.property("ktor.jwt.realm").getString()
            validate { credential ->
                if (credential.payload.getClaim("email").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}