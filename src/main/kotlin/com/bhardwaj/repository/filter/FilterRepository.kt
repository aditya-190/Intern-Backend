package com.bhardwaj.repository.filter

import com.bhardwaj.models.filter.Filter
import com.bhardwaj.models.filter.FilterResponse

interface FilterRepository {
    // For Admin
    suspend fun getFilterById(filterId: String): Filter
    suspend fun insertFilter(filter: Filter)
    suspend fun insertMultipleFilters(filters: List<Filter>)
    suspend fun updateFilter(filter: Filter): Filter
    suspend fun deleteFilter(filterId: String)
    suspend fun deleteAllFilters()

    // For Client
    suspend fun getAllFilter(page: Int, limit: Int): FilterResponse
    suspend fun getAllFilterByCategoryId(categoryId: String, page: Int, limit: Int): FilterResponse
}