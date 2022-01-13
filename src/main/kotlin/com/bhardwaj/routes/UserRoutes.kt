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

fun Route.userRoutes() {
    val userRepository: UserRepository by inject()
    val tokenManager = TokenManager(HoconApplicationConfig(ConfigFactory.load()))

    route("/user") {

        authenticate {
            // Get All Users
            get("/all") {
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                if (page < 1) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Invalid Page Number")
                    )
                }

                val users = userRepository.getAllUsers(page = page, limit = limit)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = users
                )
            }

            // Get User By User Id or Email.
            get {
                val userId = call.request.queryParameters["userId"]
                val email = call.request.queryParameters["email"]

                val response = when {
                    !userId.isNullOrEmpty() -> {
                        val user = userRepository.getUserById(userId = userId)
                        user ?: Message(message = "Required User Id Not Found.")
                    }
                    !email.isNullOrEmpty() -> {
                        val user = userRepository.getUserByEmailId(emailId = email)
                        user ?: Message(message = "Required Email Id Not Found.")
                    }
                    else -> {
                        Message(message = "ID Not Provided")
                    }
                }
                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
                )
            }

            // Login User
            post("/login") {
                val response = call.receive<User>()

                if (!response.validateUserWithoutName()) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Required Fields.")
                    )
                    return@post
                }

                val email = response.email.toString().trim()
                val password = response.password.toString().trim()

                val user = userRepository.getUserByEmailId(emailId = email)
                if (user == null) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Invalid UserName and Password.")
                    )
                    return@post
                }

                val doesPasswordMatch = BCrypt.checkpw(password, user.password)
                if (!doesPasswordMatch) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Invalid UserName and Password.")
                    )
                    return@post
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = user
                )
            }

            // Update the User
            put {
                val response = call.receive<User>()
                val isUserInDb = userRepository.getUserById(response.userId)

                val user = response.copy(password = response.hashedPassword())

                if (isUserInDb == null) {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = Message(message = "User ID Not Found.")
                    )
                    return@put
                }
                if (!user.email.isNullOrEmpty()) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Email Field Cannot Be Modified.")
                    )
                    return@put
                }

                if (!userRepository.updateUser(user)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Something Went Wrong.")
                    )
                    return@put
                }

                val result = userRepository.getUserById(user.userId)
                if (result == null) {
                    call.respond(
                        status = HttpStatusCode.InternalServerError,
                        message = Message(message = "Something Went Wrong.")
                    )
                    return@put
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = result
                )
            }

            // Delete an user.
            delete {
                val userId = call.request.queryParameters["userId"]

                if (!userId.isNullOrEmpty()) {
                    if (userRepository.deleteUser(userId)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "User Deleted Successfully.")
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.InternalServerError,
                            message = Message(message = "Something Went Wrong.")
                        )
                    }
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = Message(message = "Required User Id.")
                    )
                }
            }
        }

        // Register User.
        post {
            val response = call.receive<User>()

            if (!response.validateUser()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Required Fields.")
                )
                return@post
            }

            val user = response.copy(
                tokenId = tokenManager.generateToken(response),
                name = response.name.toString().trim(),
                email = response.email.toString().trim(),
                password = response.hashedPassword()
            )

            if (userRepository.insertUser(user)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = user
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "User Already Exists.")
                )
            }
        }
    }
}