package com.bhardwaj.routes

import com.bhardwaj.models.JobCarousel
import com.bhardwaj.models.Message
import com.bhardwaj.repository.carousel.job.JobCarouselRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.jobCarouselRoutes() {
    val jobCarouselRepository: JobCarouselRepository by inject()

    route("/jobCarousel") {

        // Get All Job Carousel.
        get("/all") {
            val jobCarousel = jobCarouselRepository.getAllJobCarousel()
            call.respond(
                status = HttpStatusCode.OK,
                message = jobCarousel
            )
        }

        // Get Job Carousel By Job Carousel ID.
        get {
            val jobCarouselId = call.request.queryParameters["jobCarouselId"]

            if (!jobCarouselId.isNullOrEmpty()) {
                val jobCarousel = jobCarouselRepository.getJobCarouselById(jobCarouselId = jobCarouselId)
                val response = jobCarousel ?: Message(message = "Required Job Carousel Id Not Found.")

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

        // Insert a Job Carousel.
        post {
            val jobCarousel = call.receive<JobCarousel>()

            if (validateJobCarousel(this, jobCarousel)) {
                if (jobCarouselRepository.insertJobCarousel(jobCarousel)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = jobCarousel
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Job Carousel Already Exists.")
                    )
                }
            }
        }

        // Update the Job Carousel.
        put {
            val jobCarousel = call.receive<JobCarousel>()
            val isJobCarouselInDb = jobCarouselRepository.getJobCarouselById(jobCarousel.jobCarouselId)

            if (validateJobCarousel(this, jobCarousel)) {
                if (isJobCarouselInDb != null) {
                    if (jobCarouselRepository.updateJobCarousel(jobCarousel)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = jobCarousel
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Job Carousel Already Exist.")
                        )
                    }
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = Message(message = "Job Carousel ID Not Found.")
                    )
                }
            }
        }

        // Delete a Job Carousel.
        delete {
            val jobCarouselId = call.request.queryParameters["jobCarouselId"]

            if (!jobCarouselId.isNullOrEmpty()) {
                if (jobCarouselRepository.deleteJobCarousel(jobCarouselId)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Job Carousel Deleted Successfully.")
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
                    message = Message(message = "Required Job Carousel Id.")
                )
            }
        }
    }
}

private suspend fun validateJobCarousel(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    jobCarousel: JobCarousel
): Boolean {
    return when {
        jobCarousel.jobCarouselImage.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Job Carousel Image Field is Required.")
            )
            false
        }
        jobCarousel.placeInCarousel.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Place In Carousel Field is Required.")
            )
            false
        }
        else -> true
    }
}