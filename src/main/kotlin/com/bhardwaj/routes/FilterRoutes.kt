package com.bhardwaj.routes

import com.bhardwaj.models.Category
import com.bhardwaj.models.Filter
import com.bhardwaj.models.Message
import com.bhardwaj.repository.filter.FilterRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun Route.filterRoutes() {
    val filterRepository: FilterRepository by inject()

    route("/filter") {

        // Get All Filters
        get("/all") {
            val filters = filterRepository.getAllFilter()
            call.respond(
                status = HttpStatusCode.OK,
                message = filters
            )
        }

        // Get Filter By Filter ID or All Filters by Category ID.
        get {
            val filterId = call.request.queryParameters["filterId"]
            val categoryId = call.request.queryParameters["categoryId"]

            val response = when {
                !filterId.isNullOrEmpty() -> {
                    val filter = filterRepository.getFilterById(filterId = filterId)
                    filter ?: Message(message = "Required Filter Id Not Found.")
                }
                !categoryId.isNullOrEmpty() -> {
                    val filter = filterRepository.getAllFilterByCategoryId(categoryId = categoryId)
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

            validateFilter(this, filter)

            if (filterRepository.insertFilter(filter)) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = filter
                )
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = Message(message = "Filter Not Created.")
                )
            }
        }

        // Update the Filter.
        put {
            val filter = call.receive<Filter>()
            val isFilterInDb = filterRepository.getFilterById(filter.filterId)

            validateFilter(this, filter)

            if (isFilterInDb != null) {
                val updatedFilter =
                    filterRepository.updateFilter(filter) ?: Message(message = "Failed to Update.")

                call.respond(
                    status = HttpStatusCode.OK,
                    message = updatedFilter
                )
            } else {
                call.respond(
                    status = HttpStatusCode.NotFound,
                    message = Message(message = "Filter ID Not Found.")
                )
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

private suspend fun validateFilter(pipelineContext: PipelineContext<Unit, ApplicationCall>, filter: Filter) {
    when {
        filter.filterName.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Filter Name Field is Required."
            )
        }

        filter.filterInCategoryId.isEmpty() -> {
            pipelineContext.call.respond(
                status = HttpStatusCode.BadRequest,
                message = "Filter In Category ID Field is Required."
            )
        }
    }
}