package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class FilterResponse(
    val totalFilters: Int? = null,
    val filtersPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val filters: List<Filter> = emptyList()
)
