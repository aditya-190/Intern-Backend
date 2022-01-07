package com.bhardwaj.repository.filter

import com.bhardwaj.models.filter.Filter
import com.bhardwaj.models.filter.FilterResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class FilterRepositoryImpl(
    private val database: CoroutineDatabase,
) : FilterRepository {
    override suspend fun getFilterById(filterId: String): Filter {
        TODO("Not yet implemented")
    }

    override suspend fun insertFilter(filter: Filter) {
        TODO("Not yet implemented")
    }

    override suspend fun insertMultipleFilters(filters: List<Filter>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFilter(filter: Filter): Filter {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFilter(filterId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllFilters() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFilter(page: Int, limit: Int): FilterResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFilterByCategoryId(categoryId: String, page: Int, limit: Int): FilterResponse {
        TODO("Not yet implemented")
    }
}