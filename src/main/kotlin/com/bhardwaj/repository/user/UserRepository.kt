package com.bhardwaj.repository.user

import com.bhardwaj.models.User

interface UserRepository {
    // For Admin
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByEmailId(emailId: String): User?

    // For Clients
    suspend fun getUserById(userId: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(userId: String): Boolean
}