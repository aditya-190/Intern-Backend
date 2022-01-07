package com.bhardwaj.routes

import com.bhardwaj.models.Message
import com.bhardwaj.models.User
import com.bhardwaj.repository.user.UserRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val userRepository: UserRepository by inject()

    route("/user") {

        // Get All Users
        get("/all") {
            val users = userRepository.getAllUsers()
            call.respond(
                status = HttpStatusCode.OK,
                message = users
            )
        }

        // Get User By User Id, Token Id or Email.
        get {
            val userId = call.request.queryParameters["userId"]
            val tokenId = call.request.queryParameters["tokenId"]
            val email = call.request.queryParameters["email"]

            val response = when {
                !userId.isNullOrEmpty() -> {
                    val user = userRepository.getUserById(userId = userId)
                    user ?: Message(message = "Required User Id Not Found.")
                }
                !tokenId.isNullOrEmpty() -> {
                    val user = userRepository.getUserByTokenId(tokenId = tokenId)
                    user ?: Message(message = "Required Token Id Not Found.")
                }
                !email.isNullOrEmpty() -> {
                    val user = userRepository.getUserByEmailId(emailId = email)
                    user ?: Message(message = "Required Email Id Not Found.")
                }
                else -> {
                    Message(message = "No ID Provided")
                }
            }
            call.respond(
                status = HttpStatusCode.OK,
                message = response
            )
        }

        // Insert an User.
        post {
            val user = call.receive<User>()
            if (userRepository.insertUser(user)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = user
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "User Not Created.")
                )
            }
        }

        // Update the User
        put {
            val user = call.receive<User>()
            val isUserInDb = userRepository.getUserById(user.userId)

            if (isUserInDb != null) {
                val updatedUser =
                    userRepository.updateUser(user) ?: Message(message = "Failed to Update.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = updatedUser
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Message(message = "User ID Not Found.")
                )
            }
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
                        message = Message(message = "Failed to Delete.")
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
}
