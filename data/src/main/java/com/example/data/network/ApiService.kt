package com.example.data.network

import com.example.data.network.model.MovieDetailResponse
import com.example.data.network.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(@Header("Authorization") apiBearer : String = ApiKey.apiBearer) : Response<MovieListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Header("Authorization") apiBearer : String = ApiKey.apiBearer,
        @Path("movie_id") movieId : Int
    ) : Response<MovieDetailResponse>
}