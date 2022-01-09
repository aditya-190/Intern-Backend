package com.bhardwaj.repository.user

import com.bhardwaj.models.User
import com.bhardwaj.models.UserCredentials

interface UserRepository {
    // For Admin
    suspend fun getAllUsers(): List<User>
    suspend fun getUserByEmailId(emailId: String): User?

    // For Clients
    suspend fun getUserById(userId: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(userId: String): Boolean

    // User Credential Table
    suspend fun getUserCredentialsByEmailId(emailId: String): UserCredentials?
    suspend fun insertUserCredentials(userCredentials: UserCredentials): Boolean
    suspend fun updateUserCredentials(userCredentials: UserCredentials): Boolean
    suspend fun deleteUserCredentials(id: String): Boolean
}