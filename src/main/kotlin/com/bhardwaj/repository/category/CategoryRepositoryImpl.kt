package com.bhardwaj.repository.category

import com.bhardwaj.models.category.Category
import com.bhardwaj.models.category.CategoryResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class CategoryRepositoryImpl(
    private val database: CoroutineDatabase,
) : CategoryRepository {
    override suspend fun getCategoryById(categoryId: String): Category {
        TODO("Not yet implemented")
    }

    override suspend fun insertCategory(category: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategory(categoryId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategory(category: Category): Category {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCategory(): CategoryResponse {
        TODO("Not yet implemented")
    }
}