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
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                if (page < 1) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Invalid Page Number")
                    )
                }

                val jobs = jobRepository.getNewJobs(page = page, limit = limit)
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

            // Fetch New Jobs.
            get("/fetch") {
                val numberOfPages = call.request.queryParameters["pages"]?.toInt() ?: 1
                val keyword = call.request.queryParameters["keyword"]
                val location = call.request.queryParameters["location"]

                if (numberOfPages < 1) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Incorrect Page Number.")
                    )
                } else {
//                    // TODO: Call Python Function Here and Check the Return Type. Something Like This
//                    if(linkedinSpider.main(numberOfPages=numberOfPages, keywords=keyword, location=location)) {
//                        call.respond(
//                            status = HttpStatusCode.OK,
//                            message = Message(message = "Process Completed.")
//                        )
//                    } else {
//                        call.respond(
//                            status = HttpStatusCode.InternalServerError,
//                            message = Message(message = "Something Went Wrong.")
//                        )
//                    }
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

            // Insert Multiple Jobs at Once.
            post("/all") {
                val jobs: List<Job> = call.receive()

                if (jobRepository.insertMultipleJob(jobs)) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = jobs
                    )
                } else {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = Message(message = "Malformed JSON File.")
                    )
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
    job: Job? = null
): Boolean {
    return when {
        job?.companyName.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Company Name Field is Required.")
            )
            false
        }
        job?.postTitle.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Post Title Field is Required.")
            )
            false
        }
        job?.postDescription.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Post Description Field is Required.")
            )
            false
        }
        job?.applyNowPage.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Apply Now Page URL Field is Required.")
            )
            false
        }
        job?.jobTitle.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Job Title Field is Required.")
            )
            false
        }
        else -> true
    }
}