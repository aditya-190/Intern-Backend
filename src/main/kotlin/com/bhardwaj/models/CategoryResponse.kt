package com.bhardwaj.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val totalCategories: Int? = null,
    val categoriesPerResponse: Int? = null,
    val previousPage: Int? = null,
    val nextPage: Int? = null,
    val lastUpdated: Long? = null,
    val categories: List<Category> = emptyList()
)
