package com.bhardwaj.models.category

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val totalCategories: Int? = null,
    val lastUpdated: Long? = null,
    val categories: List<Category> = emptyList()
)
