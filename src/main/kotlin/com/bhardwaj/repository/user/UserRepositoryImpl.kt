package com.bhardwaj.repository.user

import com.bhardwaj.models.user.User
import com.bhardwaj.models.user.UserResponse
import org.litote.kmongo.coroutine.CoroutineDatabase

class UserRepositoryImpl(
    private val database: CoroutineDatabase,
) : UserRepository {
    override suspend fun getAllUsers(page: Int, limit: Int): UserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByTokenId(tokenId: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmailId(emailId: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun insertUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: User): User {
        TODO("Not yet implemented")
    }
}