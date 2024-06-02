package com.example.data.database

import androidx.room.Dao
import androidx.room.Query
@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE userName LIKE :username AND password LIKE :password")
    suspend fun login(username: String, password: String): UserEntity

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username: String): UserEntity

    @Query("INSERT INTO user (username, password, email) VALUES (:username, :userpassword, :useremail)")
    suspend fun register(username: String, userpassword: String, useremail: String)

    @Query("UPDATE user SET userName = :newUsername, password = :newpassword, email = :newEmail WHERE username = :username")
    suspend fun updateUser(newUsername: String, newpassword: String, newEmail: String, username: String)
}