package com.bhardwaj.routes

import com.bhardwaj.models.Category
import com.bhardwaj.models.Message
import com.bhardwaj.repository.category.CategoryRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.categoryRoutes() {
    val categoryRepository: CategoryRepository by inject()

    authenticate {
        route("/category") {

            // Get All Category.
            get("/all") {
                val category = categoryRepository.getAllCategory()
                call.respond(
                    status = HttpStatusCode.OK,
                    message = category
                )
            }

            // Get Category By Category ID.
            get {
                val categoryId = call.request.queryParameters["categoryId"]

                if (!categoryId.isNullOrEmpty()) {
                    val category = categoryRepository.getCategoryById(categoryId = categoryId)
                    val response = category ?: Message(message = "Required Category Id Not Found.")

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

            // Insert a Category.
            post {
                val category = call.receive<Category>()

                if (validateCategory(this, category)) {
                    if (categoryRepository.insertCategory(category)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = category
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = Message(message = "Category Already Exists.")
                        )
                    }
                }
            }

            // Update the Category.
            put {
                val category = call.receive<Category>()
                val isCategoryInDb = categoryRepository.getCategoryById(category.categoryId)

                if (validateCategory(this, category)) {
                    if (isCategoryInDb != null) {
                        if (categoryRepository.updateCategory(category)) {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = category
                            )
                        } else {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = Message(message = "Category Already Exist.")
                            )
                        }
                    } else {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            message = Message(message = "Category ID Not Found.")
                        )
                    }
                }
            }

            // Delete a Category.
            delete {
                val categoryId = call.request.queryParameters["categoryId"]

                if (!categoryId.isNullOrEmpty()) {
                    if (categoryRepository.deleteCategory(categoryId)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Category Deleted Successfully.")
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
                        message = Message(message = "Required Category Id.")
                    )
                }
            }
        }
    }
}

private suspend fun validateCategory(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    category: Category
): Boolean {
    return when {
        category.categoryName.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Category Name Field is Required.")
            )
            false
        }
        else -> true
    }
}
