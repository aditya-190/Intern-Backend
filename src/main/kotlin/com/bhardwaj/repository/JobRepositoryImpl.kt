package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class JobRepositoryImpl(
    private val database: CoroutineDatabase,
) : JobRepository {
}