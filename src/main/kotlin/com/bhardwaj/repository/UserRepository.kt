package com.bhardwaj.repository

import com.bhardwaj.models.User

interface UserRepository {
    val users: List<User>
}