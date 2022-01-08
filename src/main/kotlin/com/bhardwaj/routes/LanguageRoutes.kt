package com.bhardwaj.routes

import com.bhardwaj.models.Language
import com.bhardwaj.models.Message
import com.bhardwaj.repository.language.LanguageRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.languageRoutes() {
    val languageRepository: LanguageRepository by inject()

    route("/language") {

        // Get All Languages
        get("/all") {
            val languages = languageRepository.getAllLanguage()
            call.respond(
                status = HttpStatusCode.OK,
                message = languages
            )
        }

        // Get Language By Language ID.
        get {
            val languageId = call.request.queryParameters["languageId"]

            if (!languageId.isNullOrEmpty()) {
                val language = languageRepository.getLanguageById(languageId = languageId)
                val response = language ?: Message(message = "Required Language Id Not Found.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "No ID Provided.")
                )
            }
        }

        // Insert a Language.
        post {
            val language = call.receive<Language>()

            validateLanguage(this, language)

            if (languageRepository.insertLanguage(language)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = language
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Language Not Created.")
                )
            }
        }

        // Update the Language.
        put {
            val language = call.receive<Language>()
            val isLanguageInDb = languageRepository.getLanguageById(language.languageId)

            validateLanguage(this, language)

            if (isLanguageInDb != null) {
                val updatedLanguage =
                    languageRepository.updateLanguage(language) ?: Message(message = "Failed to Update.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = updatedLanguage
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Message(message = "Language ID Not Found.")
                )
            }
        }

        // Delete a Language.
        delete {
            val languageId = call.request.queryParameters["languageId"]

            if (!languageId.isNullOrEmpty()) {
                if (languageRepository.deleteLanguage(languageId)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Language Deleted Successfully.")
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
                    message = Message(message = "Required Language Id.")
                )
            }
        }
    }
}

private suspend fun validateLanguage(pipelineContext: PipelineContext<Unit, ApplicationCall>, language: Language) {
    when {
        language.languageName.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Language Name Field is Required."
            )
        }

        language.languageNameInEnglish.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Language name In English Filed is Required."
            )
        }
    }
}