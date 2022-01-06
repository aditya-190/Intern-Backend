package com.bhardwaj.repository

import com.bhardwaj.models.Filter

interface FilterRepository {
    val filters: List<Filter>
}