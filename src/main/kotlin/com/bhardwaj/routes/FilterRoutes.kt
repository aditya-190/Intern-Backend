package com.bhardwaj.routes

import com.bhardwaj.models.Filter
import com.bhardwaj.models.Message
import com.bhardwaj.repository.filter.FilterRepository
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.filterRoutes() {
    val filterRepository: FilterRepository by inject()

    authenticate {
        route("/filter") {

            // Get All Filters
            get("/all") {
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                if (page < 1) {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = Message(message = "Invalid Page Number")
                    )
                }

                val filters = filterRepository.getAllFilter(page = page, limit = limit)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = filters
                )
            }

            // Get Filter By Filter ID or All Filters by Category ID.
            get {
                val filterId = call.request.queryParameters["filterId"]
                val categoryId = call.request.queryParameters["categoryId"]
                val page = call.request.queryParameters["page"]?.toInt() ?: 1
                val limit = call.request.queryParameters["limit"]?.toInt() ?: 10

                val response = when {
                    !filterId.isNullOrEmpty() -> {
                        val filter = filterRepository.getFilterById(filterId = filterId)
                        filter ?: Message(message = "Required Filter Id Not Found.")
                    }
                    !categoryId.isNullOrEmpty() -> {
                        val filter = filterRepository.getAllFilterByCategoryId(
                            categoryId = categoryId,
                            page = page,
                            limit = limit
                        )
                        filter
                    }
                    else -> {
                        Message(message = "No ID Provided.")
                    }
                }
                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
                )
            }

            // Insert a Filter.
            post {
                val filter = call.receive<Filter>()

                if (validateFilter(this, filter)) {
                    if (filterRepository.insertFilter(filter)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = filter
                        )
                    } else {
                        call.respond(
                            status = HttpStatusCode.BadRequest,
                            message = Message(message = "Filter Already Exists.")
                        )
                    }
                }
            }

            // Update the Filter.
            put {
                val filter = call.receive<Filter>()
                val isFilterInDb = filterRepository.getFilterById(filter.filterId)

                if (validateFilter(this, filter)) {
                    if (isFilterInDb != null) {
                        if (filterRepository.updateFilter(filter)) {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = filter
                            )
                        } else {
                            call.respond(
                                status = HttpStatusCode.OK,
                                message = Message(message = "Filter Already Exist.")
                            )
                        }
                    } else {
                        call.respond(
                            status = HttpStatusCode.NotFound,
                            message = Message(message = "Filter ID Not Found.")
                        )
                    }
                }
            }

            // Delete a Filter.
            delete {
                val filterId = call.request.queryParameters["filterId"]

                if (!filterId.isNullOrEmpty()) {
                    if (filterRepository.deleteFilter(filterId)) {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = Message(message = "Filter Deleted Successfully.")
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
                        message = Message(message = "Required Filter Id.")
                    )
                }
            }
        }
    }
}

private suspend fun validateFilter(
    pipelineContext: PipelineContext<Unit, ApplicationCall>,
    filter: Filter
): Boolean {
    return when {
        filter.filterName.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Filter Name Field is Required.")
            )
            false
        }
        filter.filterInCategoryId.isNullOrEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = Message(message = "Filter In Category ID Field is Required.")
            )
            false
        }
        else -> true
    }
}