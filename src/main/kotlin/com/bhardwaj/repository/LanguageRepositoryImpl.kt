package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class LanguageRepositoryImpl(
    private val database: CoroutineDatabase,
) : LanguageRepository {
}