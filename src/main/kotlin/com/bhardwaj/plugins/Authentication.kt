package com.bhardwaj.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureAuthentication() {
    authentication {
        jwt {
            val jwtAudience = environment.config.property("ktor.jwt.audience").getString()
            realm = environment.config.property("ktor.jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(environment.config.property("ktor.jwt.secret").getString()))
                    .withAudience(jwtAudience)
                    .withIssuer(environment.config.property("ktor.jwt.issuer").getString())
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}