package com.bhardwaj.routes

import com.bhardwaj.models.Message
import com.bhardwaj.models.User
import com.bhardwaj.models.UserCredentials
import com.bhardwaj.repository.user.UserRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

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
            val response = call.receive<UserCredentials>()

            if (!response.validateUserWithoutName()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Required Fields.")
                )
                return@post
            }

            val email = response.email.trim()
            val password = response.password.trim()

            val userCredentials = userRepository.getUserCredentialsByEmailId(emailId = email)
            if (userCredentials == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Invalid UserName and Password.")
                )
                return@post
            }

            val doesPasswordMatch = BCrypt.checkpw(password, userCredentials.password)
            if (!doesPasswordMatch) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Invalid UserName and Password.")
                )
                return@post
            }

            val user = userRepository.getUserCredentialsByEmailId(emailId = email)
            if (user == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Something Went Wrong.")
                )
                return@post
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = user
            )
        }

        // Register User.
        post {
            val response = call.receive<UserCredentials>()

            if (!response.validateUser()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Required Fields.")
                )
                return@post
            }

            val userCredentials = UserCredentials(
                tokenId = response.tokenId,
                fcmTokenId = response.fcmTokenId,
                name = response.name?.trim(),
                email = response.email.trim(),
                password = response.hashedPassword()
            )

            if (userRepository.insertUserCredentials(userCredentials)) {
                val user = User(
                    userId = userCredentials.id,
                    userImage = null,
                    name = userCredentials.name?.trim(),
                    email = userCredentials.email,
                    languagePreference = null,
                    pushNotificationOn = true,
                    lastLoginTime = System.currentTimeMillis(),
                    categoryList = listOf()
                )

                if (userRepository.insertUser(user)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = user
                    )
                } else {
                    userRepository.deleteUser(userId = user.userId)
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Registration Failed.")
                    )
                }
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "User Already Exists.")
                )
            }
        }

        // Update the User
        put {
            val user = call.receive<User>()
            val isUserInDb = userRepository.getUserById(user.userId)
            if (isUserInDb != null) {
                if (userRepository.updateUser(user)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = user
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "User Already Exist.")
                    )
                }
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

            println("Aditya Here")

            if (!userId.isNullOrEmpty()) {
                if (userRepository.deleteUser(userId)) {
                    if (userRepository.deleteUserCredentials(id = userId)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "User Deleted Successfully.")
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Something Went Wrong.")
                        )
                    }
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
