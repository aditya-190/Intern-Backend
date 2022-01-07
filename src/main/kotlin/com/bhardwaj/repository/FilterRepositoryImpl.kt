package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class FilterRepositoryImpl(
    private val database: CoroutineDatabase,
) : FilterRepository {
}