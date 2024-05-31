package com.example.domain.repository

import com.example.domain.model.User

interface UserRepository {

    suspend fun login(username: String, password: String) : User

    suspend fun register(username: String, password: String, email: String)
}
