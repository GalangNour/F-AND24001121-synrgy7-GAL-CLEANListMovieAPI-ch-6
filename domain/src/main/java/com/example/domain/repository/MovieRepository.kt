package com.example.domain.repository

import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList

interface MovieRepository {
    suspend fun getMovieList() : MovieList

    suspend fun getMovieDetail(movieId : Int) : MovieDetail

    suspend fun addFavoriteMovie(movie: MovieDetail)

    suspend fun getFavoriteMovieList() : List<MovieDetail>
}