package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieFavoriteEntity::class, UserEntity::class], version = 2 , exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieFavoriteDao(): MovieFavoriteDao
    abstract fun userDao(): UserDao

}