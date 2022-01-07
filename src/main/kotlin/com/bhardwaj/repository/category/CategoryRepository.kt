package com.bhardwaj.repository.category

import com.bhardwaj.models.category.Category
import com.bhardwaj.models.category.CategoryResponse

interface CategoryRepository {
    // For Admin
    suspend fun getCategoryById(categoryId: String): Category
    suspend fun insertCategory(category: Category)
    suspend fun deleteCategory(categoryId: String)
    suspend fun updateCategory(category: Category): Category

    // For Client
    suspend fun getAllCategory(): CategoryResponse
}