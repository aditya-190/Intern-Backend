package com.bhardwaj.repository.filter

import com.bhardwaj.models.Filter

interface FilterRepository {
    // For Admin
    suspend fun getFilterById(filterId: String): Filter?
    suspend fun insertFilter(filter: Filter): Boolean
    suspend fun updateFilter(filter: Filter): Boolean
    suspend fun deleteFilter(filterId: String): Boolean

    // For Client
    suspend fun getAllFilter(page: Int, limit: Int): List<Filter>
    suspend fun getAllFilterByCategoryId(categoryId: String, page: Int, limit: Int): List<Filter>
}