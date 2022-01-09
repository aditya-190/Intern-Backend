package com.bhardwaj.repository.filter

import com.bhardwaj.models.Filter
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class FilterRepositoryImpl(
    database: CoroutineDatabase,
) : FilterRepository {

    private val filterTable = database.getCollection<Filter>()

    override suspend fun getFilterById(filterId: String): Filter? {
        return filterTable.findOne(filter = Filter::filterId eq filterId)
    }

    override suspend fun insertFilter(filter: Filter): Boolean {
        if (filterTable.countDocuments(filter = Filter::filterName eq filter.filterName) > 0) return false
        return filterTable.insertOne(document = filter).wasAcknowledged()
    }

    override suspend fun updateFilter(filter: Filter): Boolean {
        if (filterTable.countDocuments(filter = Filter::filterName eq filter.filterName) > 0) return false
        return filterTable.updateOne(filter = Filter::filterId eq filter.filterId, target = filter).wasAcknowledged()
    }

    override suspend fun deleteFilter(filterId: String): Boolean {
        return filterTable.deleteOne(filter = Filter::filterId eq filterId).wasAcknowledged()
    }

    override suspend fun getAllFilter(): List<Filter> {
        return filterTable.find().ascendingSort(Filter::filterName).toList()
    }

    override suspend fun getAllFilterByCategoryId(categoryId: String): List<Filter> {
        return filterTable.find(filter = Filter::filterInCategoryId eq categoryId).ascendingSort(Filter::filterName)
            .toList()
    }
}