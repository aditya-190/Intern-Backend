package com.bhardwaj.repository.user

import com.bhardwaj.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImpl(
    database: CoroutineDatabase,
) : UserRepository {

    private val userTable = database.getCollection<User>()

    override suspend fun getAllUsers(page: Int, limit: Int): List<User> {
        return userTable.find().skip(skip = (page - 1) * limit).limit(limit = limit)
            .partial(true).descendingSort(User::lastLoginTime).toList()
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
        return userTable.updateOne(
            filter = User::userId eq user.userId,
            target = user,
            updateOnlyNotNullProperties = true
        ).wasAcknowledged()
    }
}