package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class CategoryRepositoryImpl(
    private val database: CoroutineDatabase,
) : CategoryRepository {
}