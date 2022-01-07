package com.bhardwaj.repository.category

import com.bhardwaj.models.Category

interface CategoryRepository {
    // For Admin
    suspend fun getCategoryById(categoryId: String): Category?
    suspend fun insertCategory(category: Category): Boolean
    suspend fun deleteCategory(categoryId: String): Boolean
    suspend fun updateCategory(category: Category): Category?

    // For Client
    suspend fun getAllCategory(): List<Category>
}