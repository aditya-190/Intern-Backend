package com.bhardwaj.routes

import com.bhardwaj.models.Exam
import com.bhardwaj.models.Message
import com.bhardwaj.repository.exam.ExamRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.examRoutes() {
    val examRepository: ExamRepository by inject()

    authenticate {
        route("/exam") {

            // Get All Exams.
            get("/all") {
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                if (page < 1) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Invalid Page Number")
                    )
                }

                val exams = examRepository.getNewExams(page = page, limit = limit)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = exams
                )
            }

            // Get Exam By Exam ID.
            get {
                val examId = call.request.queryParameters["examId"]

                if (!examId.isNullOrEmpty()) {
                    val exam = examRepository.getExamById(examId = examId)
                    val response = exam ?: Message(message = "Required Exam Id Not Found.")

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

            // Insert an Exam.
            post {
                val exam = call.receive<Exam>()

                if (validateExam(this, exam)) {
                    if (examRepository.insertExam(exam)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = exam
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = Message(message = "Exam Already Exists.")
                        )
                    }
                }
            }

            // Update the Exam.
            put {
                val exam = call.receive<Exam>()
                val isExamInDb = examRepository.getExamById(exam.postId)

                if (validateExam(this, exam)) {
                    if (isExamInDb != null) {
                        if (examRepository.updateExam(exam)) {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = exam
                            )
                        } else {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = Message(message = "Exam Already Exists.")
                            )
                        }
                    } else {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            message = Message(message = "Exam ID Not Found.")
                        )
                    }
                }
            }

            // Delete an exam.
            delete {
                val examId = call.request.queryParameters["examId"]

                if (!examId.isNullOrEmpty()) {
                    if (examRepository.deleteExam(examId)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Exam Deleted Successfully.")
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
                        message = Message(message = "Required Exam Id.")
                    )
                }
            }
        }
    }
}

private suspend fun validateExam(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    exam: Exam
): Boolean {
    return when {
        exam.examName.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Exam Name Field is Required.")
            )
            false
        }
        exam.examOrganiser.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Exam Organiser Field is Required.")
            )
            false
        }
        exam.lastDateToRegister.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Last Date To Register Field is Required.")
            )
            false
        }
        exam.registerPage.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Register Page Field is Required.")
            )
            false
        }
        else -> true
    }
}
