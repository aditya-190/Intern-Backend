package com.bhardwaj.repository.user

import com.bhardwaj.models.user.User
import com.bhardwaj.models.user.UserResponse

interface UserRepository {
    // For Admin
    suspend fun getAllUsers(page: Int, limit: Int): UserResponse
    suspend fun getUserByTokenId(tokenId: String): User
    suspend fun getUserByEmailId(emailId: String): User

    // For Clients
    suspend fun getUserById(userId: String): User
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(userId: String)
}