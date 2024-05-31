package com.example.data.database

import androidx.room.Dao
import androidx.room.Query
@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): UserEntity
    @Query("INSERT INTO user (username, email, password) VALUES (:username, :useremail, :userpassword)")
    suspend fun register(username: String, useremail: String, userpassword: String)
}