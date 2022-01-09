package com.bhardwaj.repository.user

import com.bhardwaj.models.User
import com.bhardwaj.models.UserCredentials
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImpl(
    database: CoroutineDatabase,
) : UserRepository {

    private val userTable = database.getCollection<User>()
    private val userCredentialTable = database.getCollection<UserCredentials>()

    override suspend fun getAllUsers(): List<User> {
        return userTable.find().descendingSort(User::lastLoginTime).toList()
    }

    override suspend fun getUserById(userId: String): User? {
        return userTable.findOne(filter = User::userId eq userId)
    }

    override suspend fun getUserByEmailId(emailId: String): User? {
        return userTable.findOne(filter = User::email eq emailId)
    }

    override suspend fun insertUser(user: User): Boolean {
        if (userTable.countDocuments(filter = User::email eq user.email) > 0) return false
        return userTable.insertOne(document = user).wasAcknowledged()
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return userTable.deleteOne(filter = User::userId eq userId).wasAcknowledged()
    }

    override suspend fun updateUser(user: User): Boolean {
        if (userTable.countDocuments(filter = User::email eq user.email) > 0) return false
        return userTable.updateOne(filter = User::userId eq user.userId, target = user).wasAcknowledged()
    }

    override suspend fun getUserCredentialsByEmailId(emailId: String): UserCredentials? {
        return userCredentialTable.findOne(filter = UserCredentials::email eq emailId)
    }

    override suspend fun insertUserCredentials(userCredentials: UserCredentials): Boolean {
        if (userCredentialTable.countDocuments(filter = UserCredentials::email eq userCredentials.email) > 0) return false
        return userCredentialTable.insertOne(document = userCredentials).wasAcknowledged()
    }

    override suspend fun updateUserCredentials(userCredentials: UserCredentials): Boolean {
        if (userCredentialTable.countDocuments(filter = UserCredentials::email eq userCredentials.email) > 0) return false
        return userCredentialTable.updateOne(filter = UserCredentials::id eq userCredentials.id, target = userCredentials).wasAcknowledged()
    }

    override suspend fun deleteUserCredentials(id: String): Boolean {
        return userCredentialTable.deleteOne(filter = UserCredentials::id eq id).wasAcknowledged()
    }
}