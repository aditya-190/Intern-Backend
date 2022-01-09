package com.bhardwaj.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.bhardwaj.models.User
import io.ktor.config.*

class TokenManager(
    config: HoconApplicationConfig
) {
    private val secret = config.property("ktor.jwt.secret").getString()
    private val issuer = config.property("ktor.jwt.issuer").getString()
    private val audience = config.property("ktor.jwt.audience").getString()

    fun generateToken(user: User): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("email", user.email)
        .sign(Algorithm.HMAC256(secret))
}