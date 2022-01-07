package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class ExamRepositoryImpl(
    private val database: CoroutineDatabase,
) : ExamRepository {
}