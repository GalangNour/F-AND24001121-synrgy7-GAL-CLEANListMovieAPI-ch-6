package com.example.data.repository

import com.example.data.database.MovieFavoriteDao
import com.example.data.database.MovieFavoriteEntity
import com.example.data.network.ApiService
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieFavoriteDao
): MovieRepository, SafeApiRequest() {

    override suspend fun getMovieList() : MovieList{
        val response = safeApiRequest {
            apiService.getMovieNowPlaying()
        }
        return response.toDomain()
    }

    override suspend fun getMovieDetail(movieId: Int): MovieDetail {
        val response = safeApiRequest {
            apiService.getMovieDetail(movieId = movieId)
        }
        return response.toDomain()
    }

    override suspend fun addFavoriteMovie(movie: MovieDetail) {
        movieDao.insert(MovieFavoriteEntity(
            id = movie.id,
            title = movie.title,
            posterPath = movie.posterPath,
            overview = movie.overview,
            releaseDate = movie.releaseDate,
            backdropPath = movie.backdropPath)
        )
    }
    override suspend fun getFavoriteMovieList(): List<MovieDetail> {
        return movieDao.getAll().map { it.toDomain() }
    }

}