package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.MovieDetail

@Entity(tableName = "movie_favorite")
data class MovieFavoriteEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo (name = "title") val title: String,
    @ColumnInfo (name = "poster_path") val posterPath: String,
    @ColumnInfo (name = "overview") val overview: String,
    @ColumnInfo (name = "release_date") val releaseDate: String,
    @ColumnInfo (name = "backdrop_path") val backdropPath: String
){
    fun toDomain(): MovieDetail {
        return MovieDetail(
            backdropPath = backdropPath,
            id = id,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title
        )
    }
}