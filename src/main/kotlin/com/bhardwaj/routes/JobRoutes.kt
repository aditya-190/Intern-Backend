package com.bhardwaj.routes

import com.bhardwaj.models.Job
import com.bhardwaj.models.Message
import com.bhardwaj.repository.job.JobRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.jobRoutes() {
    val jobRepository: JobRepository by inject()

    route("/job") {

        // Get All Jobs.
        get("/all") {
            val jobs = jobRepository.getNewJobs()
            call.respond(
                status = HttpStatusCode.OK,
                message = jobs
            )
        }

        // Get Job By Job ID.
        get {
            val jobId = call.request.queryParameters["jobId"]

            if (!jobId.isNullOrEmpty()) {
                val job = jobRepository.getJobById(jobId = jobId)
                val response = job ?: Message(message = "Required Job Id Not Found.")

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

        // Insert a Job.
        post {
            val job = call.receive<Job>()

            validateJob(this, job)

            if (jobRepository.insertJob(job)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = job
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Job Not Created.")
                )
            }
        }

        // Update the Job.
        put {
            val job = call.receive<Job>()
            val isJobInDb = jobRepository.getJobById(job.postId)

            validateJob(this, job)

            if (isJobInDb != null) {
                val updatedJob =
                    jobRepository.updateJob(job) ?: Message(message = "Failed to Update.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = updatedJob
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Message(message = "Job ID Not Found.")
                )
            }
        }

        // Delete a job.
        delete {
            val jobId = call.request.queryParameters["jobId"]

            if (!jobId.isNullOrEmpty()) {
                if (jobRepository.deleteJob(jobId)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Job Deleted Successfully.")
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
                    message = Message(message = "Required Job Id.")
                )
            }
        }
    }
}

private suspend fun validateJob(pipelineContext: PipelineContext<Unit, ApplicationCall>, job: Job) {
    when {
        job.companyName.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Company Name Field is Required."
            )
        }

        job.postTitle.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Post Title Field is Required."
            )
        }

        job.postDescription.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Post Description Field is Required."
            )
        }

        job.applyNowPage.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Apply Now Page URL Field is Required."
            )
        }

        job.jobTitle.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Job Title Field is Required."
            )
        }
    }
}