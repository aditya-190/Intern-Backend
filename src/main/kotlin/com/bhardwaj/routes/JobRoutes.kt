package com.bhardwaj.routes

import com.bhardwaj.models.Job
import com.bhardwaj.models.Message
import com.bhardwaj.repository.job.JobRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.jobRoutes() {
    val jobRepository: JobRepository by inject()

    authenticate {
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

                if (validateJob(this, job)) {
                    if (jobRepository.insertJob(job)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = job
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = Message(message = "Job Already Created.")
                        )
                    }
                }
            }

            // Update the Job.
            put {
                val job = call.receive<Job>()
                val isJobInDb = jobRepository.getJobById(job.postId)

                if (validateJob(this, job)) {
                    if (isJobInDb != null) {
                        if (jobRepository.updateJob(job)) {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = job
                            )
                        } else {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = Message(message = "Job Already Exist.")
                            )
                        }
                    } else {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            message = Message(message = "Job ID Not Found.")
                        )
                    }
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
}

private suspend fun validateJob(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    job: Job
): Boolean {
    return when {
        job.companyName.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Company Name Field is Required.")
            )
            false
        }
        job.postTitle.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Post Title Field is Required.")
            )
            false
        }
        job.postDescription.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Post Description Field is Required.")
            )
            false
        }
        job.applyNowPage.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Apply Now Page URL Field is Required.")
            )
            false
        }
        job.jobTitle.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Job Title Field is Required.")
            )
            false
        }
        else -> true
    }
}