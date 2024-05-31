package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieFavoriteDao {
    @Query("SELECT * FROM movie_favorite")
    suspend fun getAll(): List<MovieFavoriteEntity>


    @Insert
    suspend fun insert(movieFavoriteEntity: MovieFavoriteEntity)

//    @Query("INSERT INTO movie_favorite (id, title, poster_path, release_date, overview) VALUES (:id, :title, :posterPath, :releaseDate, :overview)")
//    suspend fun insert(id: Int, title: String, posterPath: String, releaseDate: String, overview: String)

}