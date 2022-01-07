package com.bhardwaj.repository

import org.litote.kmongo.coroutine.CoroutineDatabase

class UserRepositoryImpl(
    private val database: CoroutineDatabase,
) : UserRepository {
}