package com.bhardwaj.repository

import com.bhardwaj.models.Category

interface CategoryRepository {
    val categories: List<Category>
}