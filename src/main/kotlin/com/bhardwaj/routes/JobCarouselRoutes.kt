package com.bhardwaj.routes

import com.bhardwaj.models.JobCarousel
import com.bhardwaj.models.Message
import com.bhardwaj.repository.carousel.job.JobCarouselRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
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
            if (jobCarouselRepository.insertJobCarousel(jobCarousel)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = jobCarousel
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Job Carousel Not Created.")
                )
            }
        }

        // Update the Job Carousel.
        put {
            val jobCarousel = call.receive<JobCarousel>()
            val isJobCarouselInDb = jobCarouselRepository.getJobCarouselById(jobCarousel.jobCarouselId)

            if (isJobCarouselInDb != null) {
                val updatedJobCarousel =
                    jobCarouselRepository.updateJobCarousel(jobCarousel) ?: Message(message = "Failed to Update.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = updatedJobCarousel
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Message(message = "Job Carousel ID Not Found.")
                )
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