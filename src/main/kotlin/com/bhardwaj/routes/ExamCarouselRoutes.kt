package com.bhardwaj.routes

import com.bhardwaj.models.ExamCarousel
import com.bhardwaj.models.Message
import com.bhardwaj.repository.carousel.exam.ExamCarouselRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.examCarouselRoutes() {
    val examCarouselRepository: ExamCarouselRepository by inject()

    route("/examCarousel") {

        // Get All Exam Carousel.
        get("/all") {
            val examCarousel = examCarouselRepository.getAllExamCarousel()
            call.respond(
                status = HttpStatusCode.OK,
                message = examCarousel
            )
        }

        // Get Exam Carousel By Exam Carousel ID.
        get {
            val examCarouselId = call.request.queryParameters["examCarouselId"]

            if (!examCarouselId.isNullOrEmpty()) {
                val examCarousel = examCarouselRepository.getExamCarouselById(examCarouselId = examCarouselId)
                val response = examCarousel ?: Message(message = "Required Exam Carousel Id Not Found.")

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

        // Insert a Exam Carousel.
        post {
            val examCarousel = call.receive<ExamCarousel>()

            if (validateExamCarousel(this, examCarousel)) {
                if (examCarouselRepository.insertExamCarousel(examCarousel)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = examCarousel
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Exam Carousel Already Exists.")
                    )
                }
            }
        }

        // Update the Exam Carousel.
        put {
            val examCarousel = call.receive<ExamCarousel>()

            if (validateExamCarousel(this, examCarousel)) {
                val isExamCarouselInDb = examCarouselRepository.getExamCarouselById(examCarousel.examCarouselId)

                if (isExamCarouselInDb != null) {
                    if (examCarouselRepository.updateExamCarousel(examCarousel)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = examCarousel
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Exam Carousel Already Exist.")
                        )
                    }
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = Message(message = "Exam Carousel ID Not Found.")
                    )
                }
            }
        }

        // Delete a ExamCarousel.
        delete {
            val examCarouselId = call.request.queryParameters["examCarouselId"]

            if (!examCarouselId.isNullOrEmpty()) {
                if (examCarouselRepository.deleteExamCarousel(examCarouselId)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Exam Carousel Deleted Successfully.")
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
                    message = Message(message = "Required Exam Carousel Id.")
                )
            }
        }
    }
}

private suspend fun validateExamCarousel(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    examCarousel: ExamCarousel
): Boolean {
    return when {
        examCarousel.examCarouselImage.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Exam Carousel Image Field is Required.")
            )
            false
        }
        examCarousel.placeInCarousel.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Place In Carousel Field is Required.")
            )
            false
        }
        else -> true
    }
}
