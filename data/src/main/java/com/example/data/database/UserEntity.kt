package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.User

@Entity(tableName = "user")
data class UserEntity (
    @PrimaryKey val userName: String,
    @ColumnInfo(name = "password") val userPassword: String,
    @ColumnInfo(name = "email") val userEmail: String
) {
    fun toDomain(): User {
        return User(userName, userPassword, userEmail)
    }
}