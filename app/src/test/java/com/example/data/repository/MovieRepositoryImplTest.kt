package com.example.data.repository

import com.example.data.database.MovieFavoriteDao
import com.example.data.network.ApiService
import com.example.data.network.model.MovieDetailResponse
import com.example.data.network.model.MovieListResponse
import com.example.domain.model.MovieDetail
import com.example.domain.model.MovieList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Response

class MovieRepositoryImplTest {

    private lateinit var service : ApiService
    private lateinit var dao : MovieFavoriteDao
    private lateinit var repository : MovieRepositoryImpl
    private lateinit var movieList : MovieList
    private lateinit var movieDetail : MovieDetail
    private lateinit var movieListResponse : MovieListResponse
    private lateinit var movieDetailResponse: MovieDetailResponse


    @Before
    fun setUp() {

        service = mockk()
        dao = mock()
        movieList = mockk()
        movieDetail = mockk()
        movieListResponse = mockk()
        movieDetailResponse = mockk()

        repository = MovieRepositoryImpl(service, dao)

    }

    @Test
    fun getMovieList() : Unit = runBlocking {
        val respMovieList = Response.success(movieListResponse)

        coEvery {
            movieListResponse.toDomain()
        } returns movieList

        coEvery {
            service.getMovieNowPlaying()
        } returns respMovieList

        repository.getMovieList()

        coVerify {
            service.getMovieNowPlaying()
        }
    }

    @Test
    fun getMovieDetail() : Unit = runBlocking{
        val respMovieDetail = Response.success(movieDetailResponse)
        val movieId = 1

        coEvery {
            movieDetailResponse.id
        } returns movieId

        coEvery {
            movieDetailResponse.toDomain()
        } returns movieDetail

        coEvery {
            service.getMovieDetail(movieId = movieId)
        } returns respMovieDetail

        repository.getMovieDetail(movieId = movieId)

        coVerify {
            service.getMovieDetail(movieId = movieId)
        }
    }


    @Test
    fun addFavoriteMovie() {
    }

    @Test
    fun getFavoriteMovieList() {
    }
}