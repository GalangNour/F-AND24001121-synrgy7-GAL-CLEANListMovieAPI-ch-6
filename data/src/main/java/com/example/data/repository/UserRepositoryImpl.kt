package com.example.data.repository

import com.example.data.database.UserDao
import com.example.domain.model.User
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        return userDao.login(username,password).toDomain()
    }

    override suspend fun register(username: String, password: String, email: String) {
        userDao.register(username, password, email)
    }

    override suspend fun getUser(username: String): User {
        return userDao.getUser(username).toDomain()
    }

    override suspend fun updateUser(newUsername: String, newpassword: String, newEmail: String, username: String) {
        userDao.updateUser(newUsername, newpassword, newEmail, username)
    }


}