package com.example.data.repository

import com.example.data.network.ApiService
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import com.example.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
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

}